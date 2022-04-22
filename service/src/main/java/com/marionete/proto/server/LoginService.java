package com.marionete.proto.server;

import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import services.LoginRequest;
import services.LoginResponse;
import services.LoginServiceGrpc;

@Service
public class LoginService extends LoginServiceGrpc.LoginServiceImplBase {

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        responseObserver.onNext(LoginResponse.newBuilder().setToken("sample token").build());
        responseObserver.onCompleted();
    }
}
