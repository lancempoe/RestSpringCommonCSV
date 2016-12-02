package com.lancep.resource;

import com.lancep.csv.CsvDataWriter;
import com.lancep.service.LocationTimeDeltaStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.util.logging.Logger;

@Component
@Path("streamingcsv")
public class StreamingCSVMarshallerResource {

    private LocationTimeDeltaStatsService locationTimeDeltaStatsService;

    @GET
    @Produces("text/csv")
    public Response getLocationTimeDeltaStats() {
        StreamingOutput stream = os -> {
            try (CsvDataWriter CsvDataWriter = new CsvDataWriter(os)) {
                locationTimeDeltaStatsService.buildLocationTimeDeltaStatsCsv(CsvDataWriter);
            }
        };

        return Response.ok(stream)
                .type("text/csv")
                .header("Content-Disposition",  "attachment; filename=\"LocationTimeDeltaStats.csv\"")
                .build();
    }

    @Autowired
    public void setLocationTimeDeltaStatsService(LocationTimeDeltaStatsService locationTimeDeltaStatsService) {
        this.locationTimeDeltaStatsService = locationTimeDeltaStatsService;
    }
}
