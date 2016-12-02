package com.lancep.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DateTimeDataPointStatsTest {

    private static final String UNIQU_KEY = "uniquKey";
    private static final String STARTING_DELAY = "arrival";
    private static final String ENDING_DELAY = "departure";
    private static final String TOTAL_TIME = "total";
    private DateTimeDataPointStats subject;

    @Before
    public void init() {
        subject = new DateTimeDataPointStats();
    }

    @Test
    public void getAsListProducesCorrectSizeOfList() throws Exception {
        List<String> dateTimeDataPointStatsAsList = subject.getAsList();
        assertThat(dateTimeDataPointStatsAsList.size(), is(4));
    }

    @Test
    public void getAsListProducesUniqueKeyInPosition1() throws Exception {
        subject.setUniqueKey(UNIQU_KEY);
        List<String> dateTimeDataPointStatsAsList = subject.getAsList();
        assertThat(dateTimeDataPointStatsAsList.get(0), is(UNIQU_KEY));
    }

    @Test
    public void getAsListProducesEndingDelayInPosition2() throws Exception {
        subject.setStartingDelay(ENDING_DELAY);
        List<String> dateTimeDataPointStatsAsList = subject.getAsList();
        assertThat(dateTimeDataPointStatsAsList.get(1), is(ENDING_DELAY));
    }

    @Test
    public void getAsListProducesStartingDelayInPosition3() throws Exception {
        subject.setEndingDelay(STARTING_DELAY);
        List<String> dateTimeDataPointStatsAsList = subject.getAsList();
        assertThat(dateTimeDataPointStatsAsList.get(2), is(STARTING_DELAY));
    }

    @Test
    public void getAsListProducesTotalTimeInPosition4() throws Exception {
        subject.setTotalActualDelta(TOTAL_TIME);
        List<String> dateTimeDataPointStatsAsList = subject.getAsList();
        assertThat(dateTimeDataPointStatsAsList.get(3), is(TOTAL_TIME));
    }
}