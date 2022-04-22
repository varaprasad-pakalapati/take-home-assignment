package com.marionete.proto.controller;

import com.marionete.proto.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class ControllerAdvice {

    private static final String ENTRY_MESSAGE = "Entering exception handling method for '{}'";

    @ExceptionHandler({IllegalArgumentException.class,
            HttpMessageConversionException.class,
            HttpMediaTypeException.class,
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            MissingRequestHeaderException.class})
    public ErrorResponse badRequestException(Exception exception, HttpServletResponse response) {
        log.error(ENTRY_MESSAGE, exception.getClass().getName());
        ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                BAD_REQUEST.toString(), exception.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return errorResponse;
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse internalServerError(Exception exception, HttpServletResponse response) {
        log.error(ENTRY_MESSAGE, exception.getClass().getName());
        ErrorResponse errorResponse = new ErrorResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                INTERNAL_SERVER_ERROR.toString(), exception.getMessage());
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return errorResponse;
    }
}
