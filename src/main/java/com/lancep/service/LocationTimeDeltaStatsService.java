package com.lancep.service;

import com.lancep.csv.CsvDataWriter;
import com.lancep.csv.CsvProcessor;
import com.lancep.csv.CsvReader;
import com.lancep.csv.util.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Map;

import static com.lancep.csv.CsvColumnNames.LOCATIONS_TIME_OFFSETS_HEADERS;

@Component
public class LocationTimeDeltaStatsService {

    private final String LOCATIONS_DATE_TIMES_FILE_PATH = "/csv/locations_date_times.csv";
    private final String LOCATIONS_TIME_OFFSETS_FILE_PATH = "/csv/locations_time_offsets.csv";
    private ResourceLoader resourceLoader = new ResourceLoader();

    public void buildLocationTimeDeltaStatsCsv(CsvDataWriter writer) throws FileNotFoundException {

        try(InputStreamReader reader = resourceLoader.getResource(LOCATIONS_DATE_TIMES_FILE_PATH)) {
            Map<String, Integer> locationsTimeOffsets = getLocationsTimeOffsets();
            CsvProcessor.printStats(writer, reader, locationsTimeOffsets);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Unable to parse \"%s\"", LOCATIONS_DATE_TIMES_FILE_PATH));
        }

    }

    private Map<String, Integer> getLocationsTimeOffsets() throws FileNotFoundException {
        Map<String, Integer> locationsTimeOffsets;

        try(InputStreamReader reader = resourceLoader.getResource(LOCATIONS_TIME_OFFSETS_FILE_PATH))  {
            locationsTimeOffsets = CsvReader.getLocationsTimeOffsetsMap(reader, LOCATIONS_TIME_OFFSETS_HEADERS);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("CSVParser unable to parse \"%s\"", LOCATIONS_TIME_OFFSETS_FILE_PATH));
        }

        return locationsTimeOffsets;
    }

}
