package com.marionete.proto.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfo {
    private String name;
    private String surname;
    private String sex;
    private int age;
}
