package com.lancep.mapper;

import com.lancep.domain.LocationTimeOffsets;
import org.apache.commons.csv.CSVRecord;

import static com.lancep.csv.CsvColumnNames.LOCATION_CODE;
import static com.lancep.csv.CsvColumnNames.TIMEZONE_OFFSET;

public class LocationTimeOffsetMapper {

    public static LocationTimeOffsets toLocationTimeOffsets(CSVRecord record) {
        LocationTimeOffsets locationTimeOffsets = new LocationTimeOffsets();
        locationTimeOffsets.setTimezoneOffset(getTimezoneOffset(record));
        locationTimeOffsets.setLocationCode(getLocationCode(record));
        return locationTimeOffsets;
    }

    private static Integer getTimezoneOffset(CSVRecord record) {
        return Integer.valueOf(record.get(TIMEZONE_OFFSET));
    }

    private static String getLocationCode(CSVRecord record) {
        return record.get(LOCATION_CODE);
    }
}
