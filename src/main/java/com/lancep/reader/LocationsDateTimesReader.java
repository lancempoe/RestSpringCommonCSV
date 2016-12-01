package com.lancep.reader;

import com.lancep.domain.DateTimeDataPointStats;
import com.lancep.factory.LocationTimeDeltaStatsAssembly;
import com.lancep.writer.CsvDataWriter;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Logger;

import static com.lancep.util.CsvColumnNames.*;

public class LocationsDateTimesReader extends CsvReader{

    private static final Logger logger = Logger.getLogger( LocationsDateTimesReader.class.getName() );

    private Map<String, Integer> LOCATION_TIME_OFFSETS;
    private String LOCATIONS_DATE_TIMES_FILE_PATH;
    private CSVParser csvRecords;
    private InputStreamReader reader;

    private final String[] FILE_HEADER_MAPPING = {
            UNIQUE_CODE,
            UNIQUE_ID,
            STARTING_LOCATION_CODE,
            ENDING_LOCATION_CODE,
            FORECASTED_STARTING_DATE_TIME,
            ACTUAL_STARTING_DATE_TIME,
            FORECASTED_ENDING_DATE_TIME,
            ACTUAL_ENDING_DATE_TIME
    };

    public LocationsDateTimesReader(String filePath, Map<String, Integer> locationsTimeOffsets) throws FileNotFoundException {
        LOCATIONS_DATE_TIMES_FILE_PATH = filePath;
        LOCATION_TIME_OFFSETS = locationsTimeOffsets;
        reader = getFileAsStreamReader(LOCATIONS_DATE_TIMES_FILE_PATH);
    }

    public void buildLocationTimeDeltaStatsCsv(CsvDataWriter CsvDataWriter) {

        try {
            csvRecords = getCsvRecords(reader, FILE_HEADER_MAPPING);
            LocationTimeDeltaStatsAssembly factory = new LocationTimeDeltaStatsAssembly(LOCATION_TIME_OFFSETS);
            for(CSVRecord record : csvRecords) {
                DateTimeDataPointStats dateTimeDataPointStats = factory.getDateTimeDataPointStats(record);
                CsvDataWriter.printLineToCsv(dateTimeDataPointStats.getAsList());
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Unable to parse \"%s\"", LOCATIONS_DATE_TIMES_FILE_PATH));
        }
    }

    public void close() {
        if (!csvRecords.isClosed()) {
            try {
                csvRecords.close();
            } catch (IOException e) {
                logger.info("Unable to close() reader: " + e.getMessage());
            }
        }
    }
}
