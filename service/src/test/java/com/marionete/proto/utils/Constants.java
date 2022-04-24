package com.marionete.proto.utils;

public class Constants {
    public static final String USER_ACCOUNT_REQUEST_VALID = "{\"username\":\"hello\",\"password\":\"sample\"}";
    public static final String USER_ACCOUNT_REQUEST_MISSING_USERNAME = "{\"password\":\"sample\"}";
    public static final String USER_ACCOUNT_REQUEST_NULL_USERNAME = "{\"username\":null, \"password\":\"sample\"}";
    public static final String USER_ACCOUNT_REQUEST_MISSING_PASSWORD = "{\"username\":\"hello\"}";
    public static final String USER_ACCOUNT_REQUEST_NULL_PASSWORD = "{\"username\":\"hello\",\"password\":null}";
    public static final String USER_ACCOUNT_RESPONSE_VALID = "{\"accountInfo\":{\"accountNumber\":\"12345-3346-3335-4456\"},\"userInfo\":{\"name\":\"John\",\"surname\":\"Doe\",\"sex\":\"male\",\"age\":32}}";
    public static final String USER_INFO = "{\"name\":\"John\",\"surname\":\"Doe\",\"sex\":\"male\",\"age\":32}";
    public static final String ACCOUNT_INFO = "{\"accountNumber\":\"12345-3346-3335-4456\"}";
}
