package com.lancep.csv;

import com.lancep.mapper.LocationTimeOffsetMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CsvReader {

    public static Map<String, Integer> getLocationsTimeOffsetsMap(InputStreamReader reader, String[] fileHeaders) throws IOException {
        LocationTimeOffsetMapper mapper = new LocationTimeOffsetMapper();
        CSVParser parser = getRecords(reader, fileHeaders);

        Map<String, Integer> locationsTimeOffsets = new HashMap<>();
        for(CSVRecord record : parser.getRecords()) {
            locationsTimeOffsets.put(mapper.getLocationCode(record), mapper.getTimezoneOffset(record));
        }
        return locationsTimeOffsets;
    }

    public static CSVParser getRecords(InputStreamReader reader, String[] fileHeaders) throws IOException {
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(fileHeaders);
        return new CSVParser(reader, csvFileFormat);
    }
}