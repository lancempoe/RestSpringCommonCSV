package com.lancep.factory;

import com.lancep.domain.DateTimeDataPointStats;
import org.apache.commons.csv.CSVRecord;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.logging.Logger;

import static com.lancep.util.CsvColumnNames.*;

public class LocationTimeDeltaStatsFactory {

    private static final Logger logger = Logger.getLogger(LocationTimeDeltaStatsFactory.class.getName());

    private final Map<String, Integer> LOCATION_TIME_OFFSETS;

    public LocationTimeDeltaStatsFactory(Map<String, Integer> locationTimeOffsets) {
        LOCATION_TIME_OFFSETS = locationTimeOffsets;
    }

    public DateTimeDataPointStats getDateTimeDataPointStats(CSVRecord record) {
        DateTimeDataPointStats dateTimeDataPointStats = new DateTimeDataPointStats();

        dateTimeDataPointStats.setUniqueKey(getUniqueKey(record));
        dateTimeDataPointStats.setStartingDelay(getStartingDelay(record));
        dateTimeDataPointStats.setEndingDelay(getEndingDelay(record));
        dateTimeDataPointStats.setTotalActualDelta(getTotalActualTime(record));

        return dateTimeDataPointStats;
    }

    private String getUniqueKey(CSVRecord record) {
        String uniqueCode = record.get(UNIQUE_CODE);
        String uniqueId = record.get(UNIQUE_ID);
        return String.format("%s %s", uniqueCode, uniqueId);
    }

    private String getStartingDelay(CSVRecord record) {
        long deltaInMinutes = getTimeDeltaInMinutes(
                record,
                STARTING_LOCATION_CODE,
                STARTING_LOCATION_CODE,
                FORECASTED_STARTING_DATE_TIME,
                ACTUAL_STARTING_DATE_TIME);
        return String.format("%+d", deltaInMinutes);
    }

    private String getEndingDelay(CSVRecord record) {
        long deltaInMinutes = getTimeDeltaInMinutes(
                record,
                ENDING_LOCATION_CODE,
                ENDING_LOCATION_CODE,
                FORECASTED_ENDING_DATE_TIME,
                ACTUAL_ENDING_DATE_TIME);
        return String.format("%+d", deltaInMinutes);
    }

    private String getTotalActualTime(CSVRecord record) {
        long deltaInMinutes = getTimeDeltaInMinutes(
                record,
                STARTING_LOCATION_CODE,
                ENDING_LOCATION_CODE,
                ACTUAL_STARTING_DATE_TIME,
                ACTUAL_ENDING_DATE_TIME);
        return Long.toString(deltaInMinutes);
    }

    private long getTimeDeltaInMinutes(
            CSVRecord record,
            String fromLocationCode,
            String toLocationCode,
            String fromTime,
            String toTime) {
        String fromTimeZoneOffset = getTimezoneOffset(record, fromLocationCode);
        String toTimeZoneOffset = getTimezoneOffset(record, toLocationCode);

        ZonedDateTime fromDateTime = ZonedDateTime.parse(record.get(fromTime)+fromTimeZoneOffset, DateTimeFormatter.ISO_DATE_TIME);
        ZonedDateTime toDateTime = ZonedDateTime.parse(record.get(toTime)+toTimeZoneOffset, DateTimeFormatter.ISO_DATE_TIME);

        return ChronoUnit.MINUTES.between(fromDateTime, toDateTime);
    }

    /**
     *
     * @param record and @param location_code will contain an offset of a max 2 char integer
     * @return a ISO_DATE_TIME standard format time zone offset
     * ex: -1 -> -0100
     * ex: 12 -> +1200
     */
    private String getTimezoneOffset(CSVRecord record, String location_code) {
        String locationCode = record.get(location_code);
        Integer offset = getHoursOffset(locationCode);
        return String.format("%+03d", offset).concat(":00");
    }

    private Integer getHoursOffset(String locationCode) {
        boolean knownLocation = LOCATION_TIME_OFFSETS.containsKey(locationCode);
        if (!knownLocation) {
            logger.warning(String.format("Unknown LocationCode: %s", locationCode));
        }
        return knownLocation ? LOCATION_TIME_OFFSETS.get(locationCode) : 0;
    }

}
