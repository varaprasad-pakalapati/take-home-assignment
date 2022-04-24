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

import static com.marionete.proto.utils.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
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
    void shouldReturnUserAccountInfoSuccessfully() throws Exception {
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

    @Test
    void shouldReturnBadRequestMissingUsername() throws Exception {
        final UserAccountRequest request = RequestResponseBuilder.builder().withBody(USER_ACCOUNT_REQUEST_MISSING_USERNAME)
                .buildUserAccountRequest();

        mockMvc.perform(post(URL)
                .header("content-type", "application/json")
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andDo(logResultHandler)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userAccountService, never()).getUserAccountDetails(any());
    }

    @Test
    void shouldReturnBadRequestNullUsername() throws Exception {
        final UserAccountRequest request = RequestResponseBuilder.builder().withBody(USER_ACCOUNT_REQUEST_NULL_USERNAME)
                .buildUserAccountRequest();

        mockMvc.perform(post(URL)
                .header("content-type", "application/json")
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andDo(logResultHandler)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userAccountService, never()).getUserAccountDetails(any());
    }

    @Test
    void shouldReturnBadRequestMissingPassword() throws Exception {
        final UserAccountRequest request = RequestResponseBuilder.builder().withBody(USER_ACCOUNT_REQUEST_MISSING_PASSWORD)
                .buildUserAccountRequest();

        mockMvc.perform(post(URL)
                .header("content-type", "application/json")
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andDo(logResultHandler)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userAccountService, never()).getUserAccountDetails(any());
    }

    @Test
    void shouldReturnBadRequestNullPassword() throws Exception {
        final UserAccountRequest request = RequestResponseBuilder.builder().withBody(USER_ACCOUNT_REQUEST_NULL_PASSWORD)
                .buildUserAccountRequest();

        mockMvc.perform(post(URL)
                .header("content-type", "application/json")
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andDo(logResultHandler)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(userAccountService, never()).getUserAccountDetails(any());
    }

    @Test
    void shouldReturnInternalServerErrorWhenServiceFails() throws Exception {
        final UserAccountRequest request = RequestResponseBuilder.builder().withBody(USER_ACCOUNT_REQUEST_VALID)
                .buildUserAccountRequest();
        doThrow(RuntimeException.class).when(userAccountService).getUserAccountDetails(any());

        mockMvc.perform(post(URL)
                .header("content-type", "application/json")
                .content(new ObjectMapper().writeValueAsBytes(request))
        ).andDo(logResultHandler)
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        then(userAccountService).should().getUserAccountDetails(refEq(RequestResponseBuilder.builder()
                .withBody(USER_ACCOUNT_REQUEST_VALID).buildUserAccountRequest()));
    }
}
