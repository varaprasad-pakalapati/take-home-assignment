package com.marionete.proto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.proto.model.UserAccountRequest;
import com.marionete.proto.services.UserAccountService;
import com.marionete.proto.utils.RequestResponseBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.marionete.proto.utils.Constants.USER_ACCOUNT_REQUEST_VALID;
import static com.marionete.proto.utils.Constants.USER_ACCOUNT_RESPONSE_VALID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class UserAccountControllerTest {

    private static final ResultHandler logResultHandler = result -> log.info(result.getResponse().getContentAsString());

    private MockMvc mockMvc;

    @Mock
    private UserAccountService userAccountService;

    private final String URL = "/marionete/useraccount";

    @BeforeEach
    void setUp() {
        UserAccountController controller = new UserAccountController(userAccountService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerAdvice())
                .build();
    }

    @Test
    void shouldReturnBalanceTransferStatusSuccessfully() throws Exception {
        final UserAccountRequest request = RequestResponseBuilder.builder().withBody(USER_ACCOUNT_REQUEST_VALID)
                .buildUserAccountRequest();
        when(userAccountService.getUserAccountDetails(any()))
                .thenReturn(RequestResponseBuilder.builder().withBody(USER_ACCOUNT_RESPONSE_VALID).buildUserAccountResponse());

        mockMvc.perform(post(URL)
                .header("content-type", "application/json")
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andDo(logResultHandler)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(USER_ACCOUNT_RESPONSE_VALID));

        then(userAccountService).should().getUserAccountDetails(refEq(RequestResponseBuilder.builder()
                .withBody(USER_ACCOUNT_REQUEST_VALID).buildUserAccountRequest()));
    }
}
