package com.lancep.reader;

import org.hamcrest.collection.IsMapWithSize;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

public class LocationsTimeOffsetReaderTest {

    private LocationsTimeOffsetReader subject;

    @Test
    public void getLocationsTimeOffsetsMapReturnsANonNullValue() throws FileNotFoundException {
        subject = new LocationsTimeOffsetReader("/test/locations_time_offsets.csv");
        Map<String, Integer> locationsTimeOffsetsMap = subject.getLocationsTimeOffsetsMap();
        assertThat(locationsTimeOffsetsMap, IsMapWithSize.aMapWithSize(greaterThan(0)));
    }

    @Test
    public void correctlyParsesLocations() throws FileNotFoundException {
        subject = new LocationsTimeOffsetReader("/test/locations_time_offsets.csv");
        Map<String, Integer> locationsTimeOffsetsMap = subject.getLocationsTimeOffsetsMap();
        assertThat(locationsTimeOffsetsMap.containsKey("PDX"), is(true));
    }

    @Test
    public void correctlyParsesLocationsOffset() throws FileNotFoundException {
        subject = new LocationsTimeOffsetReader("/test/locations_time_offsets.csv");
        Map<String, Integer> locationsTimeOffsetsMap = subject.getLocationsTimeOffsetsMap();
        assertThat(locationsTimeOffsetsMap.get("PDX"), is(-7));
    }

    @Test(expected = FileNotFoundException.class)
    public void locationsTimeOffsetReaderRequiresAnActionFile() throws FileNotFoundException {
        subject = new LocationsTimeOffsetReader("nofile.csv");
    }

    @Test
    public void getLocationsTimeOffsetsMapCanHandleInvalidFileFormat() throws FileNotFoundException {
        subject = new LocationsTimeOffsetReader("/spring/applicationContext.xml");
        Map<String, Integer> locationsTimeOffsetsMap = subject.getLocationsTimeOffsetsMap();
        assertThat(locationsTimeOffsetsMap, IsMapWithSize.anEmptyMap());
    }

}