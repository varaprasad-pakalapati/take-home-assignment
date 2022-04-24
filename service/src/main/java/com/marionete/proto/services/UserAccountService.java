package com.marionete.proto.services;

import com.marionete.proto.client.AccountClient;
import com.marionete.proto.client.UserClient;
import com.marionete.proto.exceptions.AccountClientException;
import com.marionete.proto.exceptions.UserClientException;
import com.marionete.proto.model.UserAccountRequest;
import com.marionete.proto.model.UserAccountResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Service
@AllArgsConstructor
@Slf4j
public class UserAccountService {

    private final BeanFactory beanFactory;
    private final UserClient userClient;
    private final AccountClient accountClient;
    private final TokenService tokenService;

    public UserAccountResponse getUserAccountDetails(final UserAccountRequest userAccountRequest) {
        log.info("getUserAccountDetails: {}", userAccountRequest);
        final String token = tokenService.getToken(userAccountRequest);

        final UserAccountResponse userAccountResponse = new UserAccountResponse();

        CompletableFuture.allOf(CompletableFuture.supplyAsync(() ->
                        accountClient.getAccountInfo(token),
                new TraceableExecutorService(this.beanFactory,
                        Executors.newCachedThreadPool()))
                        .exceptionally(exception -> {
                            log.error("Exception occurred while calling account client. Exception: {}",
                                    exception.getMessage());
                            throw new AccountClientException(exception.getMessage());
                        })
                        .thenAcceptAsync(response -> {
                            if (response != null) {
                                userAccountResponse.setAccountInfo(response);
                            }
                        }),
                CompletableFuture.supplyAsync(() ->
                                userClient.getUserInfo(token),
                        new TraceableExecutorService(this.beanFactory,
                                Executors.newCachedThreadPool()))
                        .exceptionally(exception -> {
                            log.error("Exception occurred while calling user client. Exception: {}",
                                    exception.getMessage());
                            throw new UserClientException(exception.getMessage());
                        })
                        .thenAcceptAsync(response -> {
                            if (response != null) {
                                userAccountResponse.setUserInfo(response);
                            }
                        })).join();
        return userAccountResponse;
    }
}