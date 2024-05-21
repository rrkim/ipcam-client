package com.rrkim.constant;

public enum RequestMethod {

    GET("GET"),
    POST("POST");

    private final String requestMethod;
    RequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
}
