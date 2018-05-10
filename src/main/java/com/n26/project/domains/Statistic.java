package com.n26.project.domains;

import java.math.BigDecimal;
import java.util.Objects;

public class Statistic {

    private long count;

    private BigDecimal sum;

    private double max;

    private double min;


    public Statistic(long count, BigDecimal sum, double max, double min) {
        this.count = count;
        this.sum = (sum);
        this.max = max;
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }
    public static final Statistic INIT = new Statistic(1, BigDecimal.ZERO, Double.NaN, Double.NaN);

    public BigDecimal getAvg() {
        return count > 0 ? sum.divide(new BigDecimal(count)) : BigDecimal.ZERO;
    }

    public Statistic assign(BigDecimal amount) {
        return this.equals(INIT) ?
                new Statistic(1, amount, amount.doubleValue(), amount.doubleValue()) :
                new Statistic(++count, sum.add(amount), Math.max(max, amount.doubleValue()), Math.min(min, amount.doubleValue()));
    }

    public Statistic merge(Statistic that) {
        if (this.equals(INIT)) {
            return that;
        }
        if (that.equals(INIT)) {
            return this;
        }
        return new Statistic(this.count + that.count, this.sum.add(that.sum), Math.max(this.max, that.max), Math.min(this.min, that.min));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistic statistic = (Statistic) o;
        return count == statistic.count &&
                statistic.sum.compareTo(sum) == 0 &&
                Double.compare(statistic.max, max) == 0 &&
                Double.compare(statistic.min, min) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count, sum, max, min);
    }
}
