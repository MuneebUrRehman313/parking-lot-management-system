package com.muneeb.parkinglot.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationErrorResponse {

    private LocalDateTime timestamp;

    private  int status;

    private String error ;


    private Map<String,String> message;

    private String path ;
}
