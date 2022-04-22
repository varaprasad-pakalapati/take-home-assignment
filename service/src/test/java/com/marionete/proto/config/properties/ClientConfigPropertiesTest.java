package com.marionete.proto.config.properties;

import com.marionete.proto.config.ClientConfigProperties;
import org.junit.jupiter.api.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class ClientConfigPropertiesTest {

    @Test
    void propertiesDataTest() {
        assertPojoMethodsFor(ClientConfigProperties.class).testing(
                Method.GETTER,
                Method.SETTER,
                Method.CONSTRUCTOR
        ).areWellImplemented();
    }
}
