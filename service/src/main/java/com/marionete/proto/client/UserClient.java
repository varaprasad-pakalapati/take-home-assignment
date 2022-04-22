package com.marionete.proto.client;

import com.marionete.proto.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "UserClient",
        url = "${client.userClient}",
        configuration = ClientConfig.class)
public interface UserClient {

    @GetMapping(consumes = APPLICATION_JSON_VALUE)
    UserInfo getUserInfo(@RequestHeader(AUTHORIZATION) final String authorization);
}
