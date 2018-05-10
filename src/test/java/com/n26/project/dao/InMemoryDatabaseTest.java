package com.n26.project.dao;

import com.n26.project.BaseTest;
import com.n26.project.domains.Statistic;
import com.n26.project.exceptions.TimestampException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.function.BinaryOperator;

public class InMemoryDatabaseTest extends BaseTest {
    private InMemoryDatabase database;
    private Statistic current = Statistic.INIT;
    

    @Before
    public void setUp() throws Exception {
        database = new InMemoryDatabase(ChronoUnit.MINUTES, ChronoUnit.SECONDS, 128, () -> Statistic.INIT, () -> System.currentTimeMillis());
    }

    @Test
    public void shouldUpdateValues() throws Exception {
        Statistic assign = Statistic.INIT.assign(BigDecimal.TEN);
        database.update(System.currentTimeMillis(), (statistic) -> assign);
        Statistic statistic = database.getStatistic(System.currentTimeMillis()).getValue();
        Assert.assertEquals("Should be Equal",statistic, assign);
    }

    @Test
    public void shouldUpdateMultipleValues() throws Exception {
        database.update(System.currentTimeMillis(), (statistic) -> setCurrent(current.assign(BigDecimal.TEN)));
        database.update(System.currentTimeMillis(), (statistic) -> setCurrent(current.assign(new BigDecimal(20))));
        database.update(System.currentTimeMillis(), (statistic) -> setCurrent(current.assign(new BigDecimal(30))));

        Statistic value = database.getStatistic(System.currentTimeMillis()).getValue();
        Assert.assertEquals(3,value.getCount());
    }

    @Test(expected = TimestampException.class)
    public void shouldThrowsExceptionIfCurrentTimestampIsOld() throws Exception {
        database.update(System.currentTimeMillis() - MINUTE - FIVE_SECOND, (statistic) -> setCurrent(current.assign(BigDecimal.TEN)));
    }

    @Test
    public void shouldProcessEmptyValue() {
        Statistic value = database.process(MERGE);
        Assert.assertEquals(value, Statistic.INIT);
    }

    @Test
    public void shouldProcessSingleValue() {
        database.update(System.currentTimeMillis(), (statistic)->current.assign(new BigDecimal(100)));
        Statistic value = database.process(MERGE);
        Assert.assertEquals(value,current.assign(new BigDecimal(100)));
    }

    @Test
    public void shouldReduceMultipleValues(){
        database.update(System.currentTimeMillis(), (statistic) -> setCurrent(current.assign(BigDecimal.TEN)));
        database.update(System.currentTimeMillis(), (statistic) -> setCurrent(current.assign(new BigDecimal(20))));
        database.update(System.currentTimeMillis(), (statistic) -> setCurrent(current.assign(new BigDecimal(30))));

        Statistic processedStats = database.process(MERGE);

        Assert.assertEquals(current,processedStats);
    }


    private Statistic setCurrent(Statistic assign) {
        current = assign;
        return current;
    }


    private static final long FIVE_SECOND = Duration.ofSeconds(5).toMillis();
    private static final long MINUTE = Duration.ofMinutes(1).toMillis();
    private static final BinaryOperator<Statistic> MERGE = (a, b) -> a.merge(b);

}
