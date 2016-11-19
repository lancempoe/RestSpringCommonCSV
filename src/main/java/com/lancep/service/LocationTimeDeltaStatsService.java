package com.lancep.service;

import com.lancep.reader.LocationsTimeOffsetReader;
import com.lancep.reader.LocationsDateTimesReader;
import com.lancep.writer.CsvDataWriter;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.Map;

@Component
public class LocationTimeDeltaStatsService {

    private final String LOCATIONS_DATE_TIMES_FILE_PATH = "/csv/locations_date_times.csv";
    private final String LOCATIONS_TIME_OFFSETS_FILE_PATH = "/csv/locations_time_offsets.csv";

    public void buildLocationTimeDeltaStatsCsv(CsvDataWriter CsvDataWriter) throws FileNotFoundException {
        LocationsDateTimesReader locationsDateTimesReader = null;
        try {
            locationsDateTimesReader = new LocationsDateTimesReader(
                    LOCATIONS_DATE_TIMES_FILE_PATH,
                    getLocationsTimeOffsets());
            locationsDateTimesReader.buildLocationTimeDeltaStatsCsv(CsvDataWriter);
        } finally {
            if (locationsDateTimesReader != null) {
                locationsDateTimesReader.close();
            }
        }
    }

    private Map<String, Integer> getLocationsTimeOffsets() throws FileNotFoundException {
        LocationsTimeOffsetReader locationsTimeOffsetReader = null;
        Map<String, Integer> locationsTimeOffsets;
        try {
            locationsTimeOffsetReader = new LocationsTimeOffsetReader(LOCATIONS_TIME_OFFSETS_FILE_PATH);
            locationsTimeOffsets = locationsTimeOffsetReader.getLocationsTimeOffsetsMap();
        } finally {
            if (locationsTimeOffsetReader != null) {
                locationsTimeOffsetReader.close();
            }
        }

        return locationsTimeOffsets;
    }

}
