package com.marionete.proto.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccountResponse {
    private AccountInfo accountInfo;
    private UserInfo userInfo;
}
