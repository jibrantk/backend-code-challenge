package com.n26.project.exceptions;

import org.springframework.core.NestedRuntimeException;

public class TimestampException extends NestedRuntimeException {
    public TimestampException(String message) {
        super(message);
    }

}
