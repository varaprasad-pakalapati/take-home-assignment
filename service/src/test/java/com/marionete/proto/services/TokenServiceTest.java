package com.marionete.proto.services;

import com.marionete.proto.model.UserAccountRequest;
import com.marionete.proto.server.LoginServer;
import com.marionete.proto.utils.RequestResponseBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static com.marionete.proto.utils.Constants.USER_ACCOUNT_REQUEST_VALID;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    private LoginServer server;

    @BeforeEach
    void beforeEach() throws IOException {
        server = new LoginServer(8980);
        server.start();
    }

    @AfterEach
    void afterEach() throws InterruptedException {
        server.stop();
    }

    @Test
    void getTokenSuccessfully() throws IOException {
        final UserAccountRequest request = RequestResponseBuilder.builder().withBody(USER_ACCOUNT_REQUEST_VALID)
                .buildUserAccountRequest();
        final String token = tokenService.getToken(request);
        assertEquals("sample token", token);
    }
}
