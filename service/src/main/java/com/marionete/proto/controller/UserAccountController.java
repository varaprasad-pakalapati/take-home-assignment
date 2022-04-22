package com.marionete.proto.controller;

import com.marionete.proto.model.UserAccountRequest;
import com.marionete.proto.model.UserAccountResponse;
import com.marionete.proto.services.UserAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Validated
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/marionete/useraccount")
    public ResponseEntity<UserAccountResponse> getUserAccountDetails(@Valid @RequestBody final UserAccountRequest userAccountRequest) {
        return ResponseEntity.ok().body(userAccountService.getUserAccountDetails(userAccountRequest));
    }
}
