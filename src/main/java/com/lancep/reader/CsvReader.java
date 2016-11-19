package com.lancep.reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class CsvReader {
    protected CSVParser getCsvRecords(InputStreamReader reader, String[] fileHeaderMapping) throws IOException {
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(fileHeaderMapping);
        return new CSVParser(reader, csvFileFormat);
    }

    protected InputStreamReader getFileAsStreamReader(String filePath) throws FileNotFoundException {
        InputStream stream = this.getClass().getResourceAsStream(filePath);
        if (stream == null) {
            throw new FileNotFoundException(String.format("Unable to find \"%s\"", filePath));
        }
        return new InputStreamReader(stream);
    }

    public abstract void close();
}
