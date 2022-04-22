package com.marionete.proto.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.proto.model.AccountInfo;
import com.marionete.proto.model.UserAccountRequest;
import com.marionete.proto.model.UserAccountResponse;
import com.marionete.proto.model.UserInfo;
import org.apache.catalina.User;

import java.io.IOException;

public class RequestResponseBuilder {
    private String body;

    private RequestResponseBuilder() {
    }

    public UserAccountRequest buildUserAccountRequest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(body, UserAccountRequest.class);
    }

    public UserAccountResponse buildUserAccountResponse() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(body, UserAccountResponse.class);
    }

    public UserInfo buildUserInfo() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(body, UserInfo.class);
    }

    public AccountInfo buildAccountInfo() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(body, AccountInfo.class);
    }

    public RequestResponseBuilder withBody(final String responseBody) {
        this.body = responseBody;
        return this;
    }

    public static RequestResponseBuilder builder() {
        return new RequestResponseBuilder();
    }
}
