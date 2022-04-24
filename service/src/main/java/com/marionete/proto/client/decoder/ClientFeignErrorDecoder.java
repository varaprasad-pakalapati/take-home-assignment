package com.marionete.proto.client.decoder;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

import static feign.FeignException.errorStatus;

public class ClientFeignErrorDecoder implements ErrorDecoder {

    @Override
    public FeignException decode(String methodKey, Response response) {
        FeignException exception = feign.FeignException.errorStatus(methodKey, response);
        int status = response.status();
        if (status == 400) {
            return new FeignException.BadRequest(
                    exception.getMessage(),
                    response.request(),
                    response.request().body(),
                    response.headers());
        }

        if (status == 404) {
            return new FeignException.NotFound(
                    exception.getMessage(),
                    response.request(),
                    response.request().body(),
                    response.headers());
        }

        if (status == 401) {
            return new FeignException.Unauthorized(
                    exception.getMessage(),
                    response.request(),
                    response.request().body(),
                    response.headers());
        }

        if (status >= 500) {
            return new RetryableException(
                    response.status(),
                    exception.getMessage(),
                    response.request().httpMethod(),
                    exception,
                    null,
                    response.request());
        }
        return errorStatus(methodKey, response);
    }
}