package com.lancep.resource;

import com.lancep.csv.CsvDataWriter;
import com.lancep.service.CSVMarshallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

@SuppressWarnings("unused")
@Controller
@Path("streamingcsv")
public class CSVMarshallerResource {

    private CSVMarshallerService CSVMarshallerService;

    @GET
    @Produces("text/csv")
    public Response getLocationTimeDeltaStats() {
        StreamingOutput stream = os -> {
            try (CsvDataWriter CsvDataWriter = new CsvDataWriter(os)) {
                CSVMarshallerService.buildLocationTimeDeltaStatsCsv(CsvDataWriter);
            }
        };

        return Response.ok(stream)
                .type("text/csv")
                .header("Content-Disposition",  "attachment; filename=\"LocationTimeDeltaStats.csv\"")
                .build();
    }

    @Autowired
    public void setCSVMarshallerService(CSVMarshallerService CSVMarshallerService) {
        this.CSVMarshallerService = CSVMarshallerService;
    }
}
