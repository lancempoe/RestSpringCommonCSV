package com.lancep.factory;

import com.lancep.domain.DateTimeDataPointStats;
import com.lancep.util.CsvColumnNames;
import mockit.Expectations;
import mockit.Injectable;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DateTimeDataPointStatsFactoryTest {

    public static final String UNiQUE_CODE = "UA";
    public static final String UNIQUE_ID = "30";
    public static final String STARTING_LOCATION = "PDX";
    public static final String ENDING_LOCATION = "BOS";
    public static final String FORECASTED_STARTING_DATE = "2015-01-01T08:00:00";
    public static final String ACTUAL_STARTING_DATE = "2015-01-01T08:03:00";
    public static final String FORECASTED_ENDING_DATE = "2015-01-01T16:15:00";
    public static final String ACTION_ENDING_DATE = "2015-01-01T16:25:00";

    private final Map<String, Integer> LOCATION_TIME_OFFSETS =
            Collections.unmodifiableMap(Stream.of(
                    new AbstractMap.SimpleEntry<>(STARTING_LOCATION, -7),
                    new AbstractMap.SimpleEntry<>(ENDING_LOCATION, -4))
                    .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));
    private final LocationTimeDeltaStatsFactory subject = new LocationTimeDeltaStatsFactory(LOCATION_TIME_OFFSETS);
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
        DateTimeDataPointStats dateTimeDataPointStats = subject.getDateTimeDataPointStats(record);
        assertThat(dateTimeDataPointStats.getUniqueKey(), is(key));
    }

    @Test
    public void canBuildStartingDelay() throws IOException {
        String startingDelay = "+3";
        DateTimeDataPointStats dateTimeDataPointStats = subject.getDateTimeDataPointStats(record);
        assertThat(dateTimeDataPointStats.getStartingDelay(), is(startingDelay));
    }

    @Test
    public void canBuildEndingDelay() throws IOException {
        String endingDelay = "+10";
        DateTimeDataPointStats dateTimeDataPointStats = subject.getDateTimeDataPointStats(record);
        assertThat(dateTimeDataPointStats.getEndingDelay(), is(endingDelay));
    }

    @Test
    public void canBuildTotalTime() throws IOException {
        DateTimeDataPointStats dateTimeDataPointStats = subject.getDateTimeDataPointStats(record);
        String totalActualDelta = "322";
        assertThat(dateTimeDataPointStats.getTotalActualDelta(), is(totalActualDelta));
    }
}