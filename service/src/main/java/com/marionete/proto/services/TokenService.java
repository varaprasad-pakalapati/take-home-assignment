package com.marionete.proto.services;

import com.marionete.proto.model.UserAccountRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc;

@Service
@Slf4j
public class TokenService {

    public String getToken(final UserAccountRequest userAccountRequest) {
        log.debug("Getting token");
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8980").usePlaintext().build();
        LoginServiceGrpc.LoginServiceBlockingStub stub = LoginServiceGrpc.newBlockingStub(channel);

        LoginResponse response = stub.login(LoginRequest.newBuilder()
                .setUsername(userAccountRequest.getUsername())
                .setPassword(userAccountRequest.getPassword())
                .build());
        return response.getToken();
    }
}
