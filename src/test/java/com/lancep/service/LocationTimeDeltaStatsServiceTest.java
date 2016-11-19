package com.lancep.service;

import com.lancep.reader.LocationsTimeOffsetReader;
import com.lancep.reader.LocationsDateTimesReader;
import com.lancep.writer.CsvDataWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocationTimeDeltaStatsService.class})
public class LocationTimeDeltaStatsServiceTest {

    LocationTimeDeltaStatsService subject = new LocationTimeDeltaStatsService();

    @Mock
    CsvDataWriter CsvDataWriter;
    @Mock
    LocationsDateTimesReader locationsDateTimesReader;
    @Mock
    LocationsTimeOffsetReader locationsTimeOffsetReader;

    @Test
    public void canPrintLinesToCsv() throws Exception {
        subject.buildLocationTimeDeltaStatsCsv(CsvDataWriter);
        verify(CsvDataWriter, atLeastOnce()).printLineToCsv(anyList());
    }

    @Test
    public void canGetLocationsTimeOffsetsMap() throws Exception {
        whenNew(LocationsTimeOffsetReader.class).withAnyArguments().thenReturn(locationsTimeOffsetReader);
        when(locationsTimeOffsetReader.getLocationsTimeOffsetsMap()).thenReturn(new HashMap());
        subject.buildLocationTimeDeltaStatsCsv(CsvDataWriter);
        verify(locationsTimeOffsetReader).getLocationsTimeOffsetsMap();
    }

    @Test
    public void canCloseLocationsDateTimesReader() throws Exception {
        whenNew(LocationsTimeOffsetReader.class).withAnyArguments().thenReturn(locationsTimeOffsetReader);
        when(locationsTimeOffsetReader.getLocationsTimeOffsetsMap()).thenReturn(new HashMap());
        subject.buildLocationTimeDeltaStatsCsv(CsvDataWriter);
        verify(locationsTimeOffsetReader).close();
    }

    @Test
    public void canStartTheCsvBuildProcess() throws Exception {
        whenNew(LocationsDateTimesReader.class).withAnyArguments().thenReturn(locationsDateTimesReader);
        subject.buildLocationTimeDeltaStatsCsv(CsvDataWriter);
        verify(locationsDateTimesReader).buildLocationTimeDeltaStatsCsv(CsvDataWriter);
    }

    @Test
    public void canCloseLocationsDateTimesReader() throws Exception {
        whenNew(LocationsDateTimesReader.class).withAnyArguments().thenReturn(locationsDateTimesReader);
        subject.buildLocationTimeDeltaStatsCsv(CsvDataWriter);
        verify(locationsDateTimesReader).close();
    }


}