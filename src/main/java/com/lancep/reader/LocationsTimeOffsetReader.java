package com.lancep.reader;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LocationsTimeOffsetReader extends CsvReader {

    private static final Logger logger = Logger.getLogger( LocationsTimeOffsetReader.class.getName() );

    private final String LOCATION_CODE = "locationCode";
    private final String TIMEZONE_OFFSET = "timezoneOffset";
    private final String[] FILE_HEADER_MAPPING = {LOCATION_CODE, TIMEZONE_OFFSET};

    private final String LOCATIONS_TIME_OFFSETS_FILE_PATH;
    private CSVParser csvRecords;
    protected InputStreamReader reader;

    public LocationsTimeOffsetReader(String locationsTimeOffsetsFilePath) throws FileNotFoundException {
        LOCATIONS_TIME_OFFSETS_FILE_PATH = locationsTimeOffsetsFilePath;
        reader = getFileAsStreamReader(LOCATIONS_TIME_OFFSETS_FILE_PATH);
    }

    public Map<String, Integer> getLocationsTimeOffsetsMap() {
        Map<String, Integer> locationsTimeOffset = new HashMap<>();
        try {
            locationsTimeOffset = buildLocationsTimeOffsetMap();
        } catch (IllegalArgumentException e) {
            logger.warning(e.getMessage());
        }
        return locationsTimeOffset;
    }

    private Map<String, Integer> buildLocationsTimeOffsetMap() throws IllegalArgumentException {
        Map<String, Integer> locationsTimeOffsets = new HashMap<>();
        try {
            csvRecords = getCsvRecords(reader, FILE_HEADER_MAPPING);
            for(CSVRecord record : csvRecords) {
                locationsTimeOffsets.put(getLocationCode(record), getTimezoneOffset(record));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("CSVParser unable to parse \"%s\"", LOCATIONS_TIME_OFFSETS_FILE_PATH));
        }
        return locationsTimeOffsets;
    }

    private Integer getTimezoneOffset(CSVRecord record) {
        return Integer.valueOf(record.get(TIMEZONE_OFFSET));
    }

    private String getLocationCode(CSVRecord record) {
        return record.get(LOCATION_CODE);
    }

    public void close() {
        if (!csvRecords.isClosed()) {
            try {
                csvRecords.close();
            } catch (IOException e) {
                logger.info("Unable to close() reader: " + e.getMessage());
            }
        }
    }
}