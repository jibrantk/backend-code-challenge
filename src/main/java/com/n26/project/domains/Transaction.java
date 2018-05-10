package com.n26.project.domains;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.util.Objects;

import static com.n26.project.exceptions.TimestampException.byCondition;

public class Transaction {

    private BigDecimal amount;
    private long timestamp;

    public Transaction(){}

    public Transaction(BigDecimal amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        byCondition(timestamp);
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return timestamp == that.timestamp &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(amount, timestamp);
    }
}
