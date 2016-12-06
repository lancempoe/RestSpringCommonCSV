package com.lancep.service;

import com.lancep.csv.CsvDataWriter;
import com.lancep.errorhandling.CsvException;

public interface CSVMarshallerService {

    void buildLocationTimeDeltaStatsCsv(CsvDataWriter writer) throws CsvException;

}
