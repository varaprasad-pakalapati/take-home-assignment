package com.marionete.proto.client;

import com.marionete.proto.client.decoder.ClientFeignErrorDecoder;
import feign.Logger;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.spring.SpringFormEncoder;
import feign.httpclient.ApacheHttpClient;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Arrays;

public class ClientConfig {

    @Bean
    public Retryer retryer() {
        return new Custom();
    }

    @Bean
    public ApacheHttpClient client() {
        return new ApacheHttpClient();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ClientFeignErrorDecoder();
    }

    @Bean
    public Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new SpringFormEncoder((new SpringEncoder(messageConverters)));
    }

    @Bean
    public Decoder feignDecoder() {
        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(mappingJackson2HttpMessageConverter());
        ObjectFactory<HttpMessageConverters> objectFactory = () -> httpMessageConverters;

        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(
                Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));

        return converter;
    }

    static class Custom implements Retryer {

        private final int maxAttempts;
        private final long backoff;
        int attempt;

        public Custom() {
            this(2000, 3);
        }

        public Custom(long backoff, int maxAttempts) {
            this.backoff = backoff;
            this.maxAttempts = maxAttempts;
            this.attempt = 1;
        }

        public void continueOrPropagate(RetryableException e) {
            if (attempt++ >= maxAttempts) {
                throw e;
            }

            try {
                Thread.sleep(backoff);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public Retryer clone() {
            return new Custom(backoff, maxAttempts);
        }
    }
}
