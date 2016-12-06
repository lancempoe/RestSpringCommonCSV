package com.lancep.service.impl;

import com.google.inject.Singleton;
import com.lancep.csv.CsvDataWriter;
import com.lancep.csv.CsvProcessor;
import com.lancep.csv.CsvReader;
import com.lancep.csv.util.ResourceLoader;
import com.lancep.errorhandling.CsvException;
import com.lancep.service.CSVMarshallerService;

import java.io.InputStreamReader;
import java.util.Map;

import static com.lancep.csv.CsvColumnNames.LOCATIONS_TIME_OFFSETS_HEADERS;

@Singleton
public class CSVMarshallerServiceImpl implements CSVMarshallerService {

    private final static String LOCATIONS_DATE_TIMES_FILE_PATH = "/csv/locations_date_times.csv";
    private final static String LOCATIONS_TIME_OFFSETS_FILE_PATH = "/csv/locations_time_offsets.csv";
    private final ResourceLoader resourceLoader = new ResourceLoader();

    public void buildLocationTimeDeltaStatsCsv(CsvDataWriter writer) throws CsvException {

        try(InputStreamReader reader = resourceLoader.getResource(LOCATIONS_DATE_TIMES_FILE_PATH)) {
            Map<String, Integer> locationsTimeOffsets = getLocationsTimeOffsets();
            CsvProcessor.printStats(writer, reader, locationsTimeOffsets);
        } catch (Exception e) {
            throw new CsvException(String.format("Unable to parse \"%s\"", LOCATIONS_DATE_TIMES_FILE_PATH));
        }

    }

    private Map<String, Integer> getLocationsTimeOffsets() throws CsvException {
        Map<String, Integer> locationsTimeOffsets;

        try(InputStreamReader reader = resourceLoader.getResource(LOCATIONS_TIME_OFFSETS_FILE_PATH))  {
            locationsTimeOffsets = CsvReader.getLocationsTimeOffsetsMap(reader, LOCATIONS_TIME_OFFSETS_HEADERS);
        } catch (Exception e) {
            throw new CsvException(String.format("CSVParser unable to parse \"%s\"", LOCATIONS_TIME_OFFSETS_FILE_PATH));
        }

        return locationsTimeOffsets;
    }

}

