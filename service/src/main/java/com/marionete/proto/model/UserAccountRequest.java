package com.marionete.proto.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserAccountRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
