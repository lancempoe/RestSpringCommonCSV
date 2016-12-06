package com.lancep.csv;

import org.hamcrest.collection.IsMapWithSize;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import static com.lancep.csv.CsvColumnNames.LOCATIONS_TIME_OFFSETS_HEADERS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

/**
 * Integration tests.
 */
@SuppressWarnings("unused")
public class CsvReaderTest {

    @Test
    public void getLocationsTimeOffsetsMapReturnsANonNullValue() throws IOException {
        String fileName = "/test/locations_time_offsets.csv";
        try(InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream(fileName))) {
            Map<String, Integer> locationsTimeOffsetsMap = CsvReader
                    .getLocationsTimeOffsetsMap(reader, LOCATIONS_TIME_OFFSETS_HEADERS);
            assertThat(locationsTimeOffsetsMap, IsMapWithSize.aMapWithSize(greaterThan(0)));
        }

    }

    @Test
    public void correctlyParsesLocations() throws IOException {
        String fileName = "/test/locations_time_offsets.csv";
        try(InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream(fileName))) {
            Map<String, Integer> locationsTimeOffsetsMap = CsvReader
                    .getLocationsTimeOffsetsMap(reader, LOCATIONS_TIME_OFFSETS_HEADERS);
            assertThat(locationsTimeOffsetsMap.containsKey("PDX"), is(true));
        }
    }

    @Test
    public void correctlyParsesLocationsOffset() throws IOException {
        String fileName = "/test/locations_time_offsets.csv";
        try(InputStreamReader reader = new InputStreamReader(this.getClass().getResourceAsStream(fileName))) {
            Map<String, Integer> locationsTimeOffsetsMap = CsvReader
                    .getLocationsTimeOffsetsMap(reader, LOCATIONS_TIME_OFFSETS_HEADERS);
            assertThat(locationsTimeOffsetsMap.get("PDX"), is(-7));
        }
    }

}