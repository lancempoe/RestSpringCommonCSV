package com.lancep.service;

import com.lancep.csv.CsvDataWriter;
import com.lancep.csv.CsvProcessor;
import com.lancep.csv.CsvReader;
import com.lancep.csv.util.ResourceLoader;
import com.lancep.errorhandling.CsvException;
import com.lancep.service.impl.CSVMarshallerServiceImpl;
import mockit.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CSVMarshallerServiceTest {

    private @Tested
    CSVMarshallerServiceImpl subject;

    private @Mocked ResourceLoader resourceLoader;
    private @Mocked CsvReader csvReader;
    private @Mocked CsvProcessor processor;

    private @Injectable CsvDataWriter writer;
    private @Injectable InputStreamReader reader;

    private static final Map<String, Integer> LOCATIONS_TIME_OFFSETS;
    static {
        LOCATIONS_TIME_OFFSETS = new HashMap<>();
        LOCATIONS_TIME_OFFSETS.put("portland", -7);
    }

    @Before
    public void init() throws IOException {
        new Expectations() {{
            resourceLoader.getResource(anyString); result = reader;
            CsvReader.getLocationsTimeOffsetsMap(reader, (String[])any); result = LOCATIONS_TIME_OFFSETS; minTimes = 0;
        }};
    }

    @Test
    public void callsPrintStats() throws IOException {
        new Expectations() {{
            CsvProcessor.printStats(writer, reader, LOCATIONS_TIME_OFFSETS); times =1;
        }};

        subject.buildLocationTimeDeltaStatsCsv(writer);
    }

    @Test
    public void canGetLocationsTimeOffsetsMap() throws Exception {

        new Expectations() {{
            CsvReader.getLocationsTimeOffsetsMap(reader, (String[])any); result = LOCATIONS_TIME_OFFSETS; times = 1;
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
            CsvProcessor.printStats(writer, reader, LOCATIONS_TIME_OFFSETS); result = new IllegalArgumentException();
        }};
        subject.buildLocationTimeDeltaStatsCsv(writer);
    }

}