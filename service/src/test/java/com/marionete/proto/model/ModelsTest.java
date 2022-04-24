package com.marionete.proto.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

@ExtendWith(MockitoExtension.class)
public class ModelsTest {

    @Test
    void propertiesDataTest() {
        final Class<?>[] propertiesClasses = {
                UserAccountRequest.class
        };
        for (Class<?> propertiesClass : propertiesClasses)
            assertPojoMethodsFor(propertiesClass).testing(
                    Method.GETTER,
                    Method.SETTER,
                    Method.TO_STRING,
                    Method.EQUALS,
                    Method.CONSTRUCTOR,
                    Method.HASH_CODE
            ).areWellImplemented();
    }

    @Test
    void GetterSetterTest() {
        final Class<?>[] propertiesClasses = {
                AccountInfo.class,
                UserInfo.class,
                ErrorResponse.class,
                Token.class,
                UserAccountResponse.class
        };
        for (Class<?> propertiesClass : propertiesClasses)
            assertPojoMethodsFor(propertiesClass).testing(
                    Method.GETTER,
                    Method.SETTER
            ).areWellImplemented();
    }

    @Test
    void constructorTest() {
        assertPojoMethodsFor(ErrorResponse.class).testing(
                Method.CONSTRUCTOR
        ).areWellImplemented();
    }
}
