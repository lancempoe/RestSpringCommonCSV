package com.lancep.csv;

public class CsvColumnNames {

    public static final String UNIQUE_CODE = "uniqueCode";
    public static final String UNIQUE_ID = "uniqueId";
    public static final String STARTING_LOCATION_CODE = "startingLocationCode";
    public static final String ENDING_LOCATION_CODE = "endingLocationCode";
    public static final String FORECASTED_STARTING_DATE_TIME = "forecastedStartingDateTime";
    public static final String ACTUAL_STARTING_DATE_TIME = "actualStartingDateTime";
    public static final String FORECASTED_ENDING_DATE_TIME = "forecastedEndingDateTime";
    public static final String ACTUAL_ENDING_DATE_TIME = "actualEndingDateTeime";

    public static final String LOCATION_CODE = "locationCode";
    public static final String TIMEZONE_OFFSET = "timezoneOffset";

    public static final String[] LOCATIONS_DATE_TIMES_FILE_HEADERS = {
            UNIQUE_CODE,
            UNIQUE_ID,
            STARTING_LOCATION_CODE,
            ENDING_LOCATION_CODE,
            FORECASTED_STARTING_DATE_TIME,
            ACTUAL_STARTING_DATE_TIME,
            FORECASTED_ENDING_DATE_TIME,
            ACTUAL_ENDING_DATE_TIME
    };

    public static final String[] LOCATIONS_TIME_OFFSETS_HEADERS = {
            LOCATION_CODE,
            TIMEZONE_OFFSET
    };

}
