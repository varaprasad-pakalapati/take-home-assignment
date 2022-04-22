package com.marionete.proto.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The type Client config properties.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "client")
public class ClientConfigProperties {

    private String accountClient;
    private String userClient;
    private int connectionTimeout;
    private int connectionRequestTimeout;
    private int readTimeout;
}
