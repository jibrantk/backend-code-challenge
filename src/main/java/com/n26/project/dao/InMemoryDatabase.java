package com.n26.project.dao;


import com.n26.project.domains.Statistic;
import com.n26.project.exceptions.TimestampException;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.MILLIS;

public class InMemoryDatabase {

    protected static class Pair {
        private final long index;
        private final AtomicReference<Statistic> value;

        public Pair(long index, Statistic value) {
            this.index = index;
            this.value = new AtomicReference<>(value);
        }

        public void update(UnaryOperator<Statistic> updater) {
            value.updateAndGet(updater);
        }

        public Statistic getValue() {
            return value.get();
        }

        public long getIndex() {
            return index;
        }
    }

    private final Supplier<Long> now;
    private final Supplier<Statistic> factory;

    private final TemporalUnit target;
    private final TemporalUnit groupBy;

    private final AtomicReferenceArray<Pair> memory;

    public static InMemoryDatabase lastMinute(Supplier<Statistic> factory) {
        return new InMemoryDatabase(ChronoUnit.MINUTES, ChronoUnit.SECONDS, 64, factory);
    }

    public InMemoryDatabase(TemporalUnit target, TemporalUnit groupBy, int bufferSize, Supplier<Statistic> factory) {
        this(target, groupBy, bufferSize, factory, System::currentTimeMillis);
    }


    protected InMemoryDatabase(TemporalUnit target, TemporalUnit groupBy, int bufferSize, Supplier<Statistic> factory, Supplier<Long> now) {
        this.now = now;
        this.factory = factory;
        this.target = target;
        this.groupBy = groupBy;
        this.memory = new AtomicReferenceArray<>(bufferSize);
    }

    public void update(Long timestamp, UnaryOperator<Statistic> updater) {
        getStatistic(timestamp).update(updater);
    }

    public Statistic process(BinaryOperator<Statistic> reducer) {
        return getReferenceFromStream().reduce(factory.get(), reducer);
    }

    public Pair getStatistic(long timestamp) {
        int index = checkedIndexFor(timestamp);
        int offset = offset(index);
        return memory.updateAndGet(offset, value -> actual(index, value));
    }

    private Stream<Statistic> getReferenceFromStream() {
        long now = this.now.get();

        int firstIndex = minIndex(now);
        int lastIndex = currentIndex(now);

        return IntStream.rangeClosed(firstIndex, lastIndex)
                .mapToObj(index -> fromBackLog(index, memory.get(offset(index))))
                .filter(Objects::nonNull)
                .map(Pair::getValue);
    }

    private Pair fromBackLog(int index, Pair pair) {
        return pair != null && pair.getIndex() == index ? pair : null;
    }

    private Pair actual(int index, Pair value) {
        return value == null || value.getIndex() < index ? new Pair(index, factory.get()) : value;
    }

    private int offset(int index) {
        return index % memory.length();
    }

    private int currentIndex(long timestamp) {
        return (int) Duration.of(timestamp, MILLIS).get(groupBy);
    }

    private int minIndex(long timestamp) {
        return (int) Duration.of(timestamp, MILLIS).minus(1, target).get(groupBy);
    }

    private int checkedIndexFor(long timestamp) {
        long now = this.now.get();

        int minimalIndex = minIndex(now);
        int maximalIndex = currentIndex(now);

        int index = currentIndex(timestamp);

        TimestampException.valid(index >= minimalIndex, "Timestamp far 60 Seconds.");
        TimestampException.valid(index <= maximalIndex, "Timestamp is within 60 Seconds.");

        return index;
    }
}
