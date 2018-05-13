package com.n26.project.util;


import com.n26.project.exceptions.TimestampException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidatorContext;

import static com.n26.project.util.Constants.getCurrentTimestamp;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class TransactionValidator {
    private Long acceptTimestampInSec;

    @Autowired
    TransactionValidator(Long acceptTimestampInSec) {
        this.acceptTimestampInSec = acceptTimestampInSec;
    }

    public boolean isValid(Long timestamp) {
        boolean valid = SECONDS.convert(getCurrentTimestamp() - timestamp, MILLISECONDS) < acceptTimestampInSec;
        if (!valid) throw new TimestampException("Old date");
        return valid;
    }
}
