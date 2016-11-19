package com.lancep.reader;

import com.lancep.writer.CsvDataWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LocationsDateTimesReaderTest {

    private LocationsDateTimesReader subject;
    private final Map<String, Integer> locationsTimeOffsets =
        Collections.unmodifiableMap(Stream.of(
            new SimpleEntry<>("PDX", -7),
            new SimpleEntry<>("BOS", -4))
            .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));
    @Mock
    CsvDataWriter CsvDataWriter;

    @Test
    public void canPrintLinesToCsv() throws Exception {
        subject = new LocationsDateTimesReader("/test/locations_date_times.csv", locationsTimeOffsets);
        subject.buildLocationTimeDeltaStatsCsv(CsvDataWriter);

        verify(CsvDataWriter).printLineToCsv(anyList());
    }

    @Test(expected = FileNotFoundException.class)
    public void locationsDateTimesReaderRequiresAnActionFile() throws FileNotFoundException {
        subject = new LocationsDateTimesReader("nofile.csv", locationsTimeOffsets);
    }


    @Test
    public void canHandleUnknownAirlines() throws IOException {
        subject = new LocationsDateTimesReader("/csv/locations_date_times.csv", locationsTimeOffsets);
        subject.buildLocationTimeDeltaStatsCsv(CsvDataWriter);

        verify(CsvDataWriter, atLeastOnce()).printLineToCsv(anyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void locationsDateTimesRequiresCorrectFileFormat() throws IOException {
        subject = new LocationsDateTimesReader("/spring/applicationContext.xml", locationsTimeOffsets);
        subject.buildLocationTimeDeltaStatsCsv(CsvDataWriter);
    }
}