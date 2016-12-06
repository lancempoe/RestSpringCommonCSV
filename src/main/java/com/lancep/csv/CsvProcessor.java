package com.lancep.csv;

import com.lancep.domain.DateTimeDataPointStats;
import com.lancep.mapper.DateTimeDataPointStatsMapper;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class CsvProcessor {

    public static void printStats(CsvDataWriter writer,
                           InputStreamReader reader,
                           Map<String, Integer> locationTimeOffsets) throws IOException {
        DateTimeDataPointStatsMapper mapper = new DateTimeDataPointStatsMapper(locationTimeOffsets);
        CSVParser records = CsvReader.getRecords(reader, CsvColumnNames.LOCATIONS_DATE_TIMES_FILE_HEADERS);
        for(CSVRecord record : records) {
            printStat(writer, mapper, record);
        }
    }

    private static void printStat(
            CsvDataWriter writer,
            DateTimeDataPointStatsMapper mapper,
            CSVRecord record) throws IOException {
        DateTimeDataPointStats stats = mapper.toDateTimeDataPointStats(record);
        List<String> statsAsList = stats.getAsList();
        writer.printLineToCsv(statsAsList);
    }

}
