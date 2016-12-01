package com.lancep.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.logging.Logger;

public class CsvDataWriter {

    private static final Logger logger = Logger.getLogger( CsvDataWriter.class.getName() );

    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
    private final int flushInterval = 100;

    private final BufferedWriter writer;
    private final CSVPrinter csvPrinter;
    private int lineCount = 0;

    public CsvDataWriter(OutputStream os) throws IOException {
        OutputStreamWriter out = new OutputStreamWriter(os);
        writer = new BufferedWriter(out);
        csvPrinter = new CSVPrinter(writer, csvFileFormat);
    }

    public void printLineToCsv(List<String> airlineStats) throws IOException {
        csvPrinter.printRecord(airlineStats);
        logger.info(String.format("Printing %d marshaled csv row", lineCount));
        if (shouldFlush(++lineCount)) {
            logger.fine("Flushing stream");
            csvPrinter.flush();
        }
    }

    public void close() throws IOException {
        logger.info(String.format("Finished printing %d rows", lineCount));

        csvPrinter.flush();
        csvPrinter.close();
    }

    /**
     * Flushing at intervals will keep the app memory usage down. The user will see
     * progress each time the writer is flushed.
     * @return
     * @param lineCount
     */
    public boolean shouldFlush(int lineCount) {
        return lineCount % flushInterval == 0;
    }
}
