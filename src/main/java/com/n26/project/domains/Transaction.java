package com.n26.project.domains;


import java.util.Objects;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

public class Transaction {

    private Double amount;
    private Long timestamp;

    public Transaction(){}

    public Transaction(Double amount, Long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
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

    public Long toMin() {
        return MINUTES.convert(timestamp, MILLISECONDS);
    }

}
