package com.lancep.mapper;

import org.apache.commons.csv.CSVRecord;

import static com.lancep.csv.CsvColumnNames.LOCATION_CODE;
import static com.lancep.csv.CsvColumnNames.TIMEZONE_OFFSET;

public class LocationTimeOffsetMapper {

    public Integer getTimezoneOffset(CSVRecord record) {
        return Integer.valueOf(record.get(TIMEZONE_OFFSET));
    }

    public String getLocationCode(CSVRecord record) {
        return record.get(LOCATION_CODE);
    }
}
