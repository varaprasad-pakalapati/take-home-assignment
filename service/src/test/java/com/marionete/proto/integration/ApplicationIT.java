package com.marionete.proto.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.backends.AccountInfoMock;
import com.marionete.backends.UserInfoMock;
import com.marionete.proto.model.UserAccountRequest;
import com.marionete.proto.model.UserAccountResponse;
import com.marionete.proto.server.LoginServer;
import com.marionete.proto.utils.RequestResponseBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;

import static com.marionete.proto.utils.Constants.USER_ACCOUNT_REQUEST_VALID;
import static com.marionete.proto.utils.Constants.USER_ACCOUNT_RESPONSE_VALID;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private Integer port;

    private LoginServer server;

    @BeforeEach
    void beforeEach() throws IOException {
        UserInfoMock.start();
        AccountInfoMock.start();
        server = new LoginServer(8980);
        server.start();
    }

    @AfterEach
    void afterEach() throws InterruptedException {
        server.stop();
    }

    @Test
    void integrationTest() throws IOException {
        final UserAccountRequest request = RequestResponseBuilder.builder().withBody(USER_ACCOUNT_REQUEST_VALID)
                .buildUserAccountRequest();
        UserAccountResponse userAccountResponse = this.testRestTemplate
                .postForObject("http://localhost:" + port + "/marionete/useraccount", request, UserAccountResponse.class);

        assertEquals(USER_ACCOUNT_RESPONSE_VALID, new ObjectMapper().writeValueAsString(userAccountResponse));
    }
}
