package com.lancep.service;

import com.google.common.collect.ImmutableMap;
import com.lancep.resource.errorhandling.CsvException;
import com.lancep.csv.CsvDataWriter;
import com.lancep.csv.CsvProcessor;
import com.lancep.csv.CsvReader;
import com.lancep.csv.util.ResourceLoader;
import mockit.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class LocationTimeDeltaStatsServiceTest {

    private @Tested LocationTimeDeltaStatsService subject;

    private @Mocked ResourceLoader resourceLoader;
    private @Mocked CsvReader csvReader;
    private @Mocked CsvProcessor processor;

    private @Injectable CsvDataWriter writer;
    private @Injectable InputStreamReader reader;

    private static final Map<String, Integer> locationsTimeOffsets = ImmutableMap.<String, Integer>builder()
            .put("portland", -7)
            .build();


    @Before
    public void init() throws IOException {
        new Expectations() {{
            resourceLoader.getResource(anyString); result = reader;
            CsvReader.getLocationsTimeOffsetsMap(reader, (String[])any); result = locationsTimeOffsets; minTimes = 0;
        }};
    }

    @Test
    public void callsPrintStats() throws IOException {
        new Expectations() {{
            CsvProcessor.printStats(writer, reader, locationsTimeOffsets); times =1;
        }};

        subject.buildLocationTimeDeltaStatsCsv(writer);
    }

    @Test
    public void canGetLocationsTimeOffsetsMap() throws Exception {

        new Expectations() {{
            CsvReader.getLocationsTimeOffsetsMap(reader, (String[])any); result = locationsTimeOffsets; times = 1;
        }};

        subject.buildLocationTimeDeltaStatsCsv(writer);
    }

    @Test
    public void closesOpenReaders() throws Exception {
        subject.buildLocationTimeDeltaStatsCsv(writer);
        new Verifications() {{
            reader.close(); minTimes = 1;
        }};
    }

    @Test(expected = CsvException.class)
    public void canHandleErrorsGettingLocations() throws IOException {
        new Expectations() {{
            CsvReader.getLocationsTimeOffsetsMap(reader, (String[])any); result = new IllegalArgumentException();
        }};
        subject.buildLocationTimeDeltaStatsCsv(writer);
    }

    @Test(expected = CsvException.class)
    public void canHandleErrorsWhileProcessing() throws IOException {
        new Expectations() {{
            CsvProcessor.printStats(writer, reader, locationsTimeOffsets); result = new IllegalArgumentException();
        }};
        subject.buildLocationTimeDeltaStatsCsv(writer);
    }

}