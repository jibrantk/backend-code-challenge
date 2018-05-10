package com.n26.project.exceptions;

import org.springframework.core.NestedRuntimeException;

import java.time.Duration;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.SECONDS;

public class TimestampException extends NestedRuntimeException {


    public static Supplier<Long> CURRENT_TIME_STAMP = System::currentTimeMillis;

    public TimestampException(String message) {
        super(message);
    }

    public static void byCondition(Long value) {
        Duration age = Duration.of(60, SECONDS);
        valid(value == null || CURRENT_TIME_STAMP.get() - value <= age.toMillis(), "");
    }

    public static void valid(boolean condition, String message) {
        if (!condition) {
            throw new TimestampException(message);
        }
    }
}
