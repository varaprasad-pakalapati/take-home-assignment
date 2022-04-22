package com.marionete.proto.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.proto.client.AccountClient;
import com.marionete.proto.client.UserClient;
import com.marionete.proto.model.AccountInfo;
import com.marionete.proto.model.UserAccountRequest;
import com.marionete.proto.model.UserAccountResponse;
import com.marionete.proto.model.UserInfo;
import com.marionete.proto.utils.RequestResponseBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.BeanFactory;

import java.io.IOException;

import static com.marionete.proto.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserAccountServiceTest {
    @InjectMocks
    private UserAccountService userAccountService;

    @Mock
    private BeanFactory beanFactory;

    @Mock
    private UserClient userClient;

    @Mock
    private AccountClient accountClient;

    @Mock
    private TokenService tokenService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void successfulResponseTest() throws IOException {
        final UserAccountRequest request = RequestResponseBuilder.builder().withBody(USER_ACCOUNT_REQUEST_VALID)
                .buildUserAccountRequest();
        final UserInfo userInfo = RequestResponseBuilder.builder().withBody(USER_INFO).buildUserInfo();
        final AccountInfo accountInfo = RequestResponseBuilder.builder().withBody(ACCOUNT_INFO).buildAccountInfo();

        given(accountClient.getAccountInfo(anyString())).willReturn(accountInfo);
        given(userClient.getUserInfo(anyString())).willReturn(userInfo);
        given(tokenService.getToken(any())).willReturn("sample token");
        UserAccountResponse actualResponse = userAccountService.getUserAccountDetails(request);
        assertEquals(USER_ACCOUNT_RESPONSE_VALID, mapper.writeValueAsString(actualResponse));
    }
}
