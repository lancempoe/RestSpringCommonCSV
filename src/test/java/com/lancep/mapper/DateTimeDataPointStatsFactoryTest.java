package com.lancep.mapper;

import com.lancep.csv.CsvColumnNames;
import com.lancep.domain.DateTimeDataPointStats;
import mockit.Expectations;
import mockit.Injectable;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("unused")
public class DateTimeDataPointStatsFactoryTest {

    private static final String UNiQUE_CODE = "UA";
    private static final String UNIQUE_ID = "30";
    private static final String STARTING_LOCATION = "PDX";
    private static final String ENDING_LOCATION = "BOS";
    private static final String FORECASTED_STARTING_DATE = "2015-01-01T08:00:00";
    private static final String ACTUAL_STARTING_DATE = "2015-01-01T08:03:00";
    private static final String FORECASTED_ENDING_DATE = "2015-01-01T16:15:00";
    private static final String ACTION_ENDING_DATE = "2015-01-01T16:25:00";

    private static final Map<String, Integer> LOCATION_TIME_OFFSETS;
    static {
        LOCATION_TIME_OFFSETS = new HashMap<>();
        LOCATION_TIME_OFFSETS.put(STARTING_LOCATION, -7);
        LOCATION_TIME_OFFSETS.put(ENDING_LOCATION, -4);
    }

    private final DateTimeDataPointStatsMapper subject = new DateTimeDataPointStatsMapper(LOCATION_TIME_OFFSETS);
    @Injectable private CSVRecord record;

    @Before
    public void init() {
        new Expectations() {{
            record.get(CsvColumnNames.UNIQUE_CODE); result = UNiQUE_CODE;
            record.get(CsvColumnNames.UNIQUE_ID); result = UNIQUE_ID;
            record.get(CsvColumnNames.STARTING_LOCATION_CODE); result = STARTING_LOCATION;
            record.get(CsvColumnNames.ENDING_LOCATION_CODE); result = ENDING_LOCATION;
            record.get(CsvColumnNames.FORECASTED_STARTING_DATE_TIME); result = FORECASTED_STARTING_DATE;
            record.get(CsvColumnNames.ACTUAL_STARTING_DATE_TIME); result = ACTUAL_STARTING_DATE;
            record.get(CsvColumnNames.FORECASTED_ENDING_DATE_TIME); result = FORECASTED_ENDING_DATE;
            record.get(CsvColumnNames.ACTUAL_ENDING_DATE_TIME); result = ACTION_ENDING_DATE;
        }};
    }

    @Test
    public void canBuildUniqueKey() throws IOException {
        String key = UNiQUE_CODE + " " + UNIQUE_ID;
        DateTimeDataPointStats dateTimeDataPointStats = subject.toDateTimeDataPointStats(record);
        assertThat(dateTimeDataPointStats.getUniqueKey(), is(key));
    }

    @Test
    public void canBuildStartingDelay() throws IOException {
        String startingDelay = "+3";
        DateTimeDataPointStats dateTimeDataPointStats = subject.toDateTimeDataPointStats(record);
        assertThat(dateTimeDataPointStats.getStartingDelay(), is(startingDelay));
    }

    @Test
    public void canBuildEndingDelay() throws IOException {
        String endingDelay = "+10";
        DateTimeDataPointStats dateTimeDataPointStats = subject.toDateTimeDataPointStats(record);
        assertThat(dateTimeDataPointStats.getEndingDelay(), is(endingDelay));
    }

    @Test
    public void canBuildTotalTime() throws IOException {
        DateTimeDataPointStats dateTimeDataPointStats = subject.toDateTimeDataPointStats(record);
        String totalActualDelta = "322";
        assertThat(dateTimeDataPointStats.getTotalActualDelta(), is(totalActualDelta));
    }
}