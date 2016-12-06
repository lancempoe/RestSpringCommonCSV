package com.lancep.csv;

import com.lancep.domain.LocationTimeOffsets;
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
        CSVParser parser = getRecords(reader, fileHeaders);

        Map<String, Integer> locationsTimeOffsets = new HashMap<>();
        for(CSVRecord record : parser.getRecords()) {
            LocationTimeOffsets locationTimeOffsets = LocationTimeOffsetMapper.toLocationTimeOffsets(record);
            locationsTimeOffsets.put(locationTimeOffsets.getLocationCode(), locationTimeOffsets.getTimezoneOffset());
        }
        return locationsTimeOffsets;
    }

    public static CSVParser getRecords(InputStreamReader reader, String[] fileHeaders) throws IOException {
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(fileHeaders);
        return new CSVParser(reader, csvFileFormat);
    }
}