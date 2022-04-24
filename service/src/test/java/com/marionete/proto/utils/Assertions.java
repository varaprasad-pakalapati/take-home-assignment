package com.marionete.proto.utils;

import org.hamcrest.Matcher;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Assertions {

    @SuppressWarnings("unchecked")
    public static void assertThatThrows(CallableWithExceptions callable, Matcher matcher, String message) {
        boolean doThrow = true;
        try {
            callable.call();
            doThrow = false;
        } catch (Throwable throwable) {
            assertThat(throwable, matcher);
            assertThat("wrong message", throwable.getMessage(), is(message));
        }
        assertThat("Call did not thrown", doThrow, is(true));

    }

    @FunctionalInterface
    public interface CallableWithExceptions {
        void call() throws Throwable;
    }
}
