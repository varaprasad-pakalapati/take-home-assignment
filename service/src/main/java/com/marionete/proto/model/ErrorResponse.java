package com.marionete.proto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    private int httpCode;
    private String httpMessage;
    private String moreInformation;
}
