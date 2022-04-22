package com.marionete.proto.client;

import com.marionete.proto.model.AccountInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "AccountClient",
        url = "${client.accountClient}",
        configuration = ClientConfig.class)
public interface AccountClient {

    @GetMapping(consumes = APPLICATION_JSON_VALUE)
    AccountInfo getAccountInfo(@RequestHeader(AUTHORIZATION) final String authorization);
}
