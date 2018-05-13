package com.n26.project.domains;

import java.math.BigDecimal;
import java.util.Objects;

public class Statistic {

    private long count;

    private double sum;

    private double max;

    private double min;

    private double avg;

    public void setAmount(Double amount) {
        count++;
        sum = sum + amount;
        min = Math.min(min == 0.0 ? amount:min,amount);
        max = Math.max(max,amount);
        avg = Math.ceil(sum / count);
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }
}
