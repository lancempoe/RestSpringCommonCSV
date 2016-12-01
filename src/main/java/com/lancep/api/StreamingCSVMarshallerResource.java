package com.lancep.api;

import com.lancep.service.LocationTimeDeltaStatsService;
import com.lancep.csv.CsvDataWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

@Component
@Path("streamingcsv")
public class StreamingCSVMarshallerResource extends Resouce {

    private static final Logger logger = Logger.getLogger( StreamingCSVMarshallerResource.class.getName() );

    @Autowired
    LocationTimeDeltaStatsService locationTimeDeltaStatsService;

    @GET
    @Produces("text/csv")
    public Response getLocationTimeDeltaStats() {
        StreamingOutput stream = os -> {
            try {
                CsvDataWriter CsvDataWriter = new CsvDataWriter(os);
                locationTimeDeltaStatsService.buildLocationTimeDeltaStatsCsv(CsvDataWriter);
                CsvDataWriter.close();
            } catch(IllegalArgumentException | FileNotFoundException e) {
                logger.warning(e.getMessage());
                respondWithInternalError("Failed to build CSV file");
            } finally {
                os.close();
            }
        };

        return Response.ok(stream)
                .type("text/csv")
                .header("Content-Disposition",  "attachment; filename=\"LocationTimeDeltaStats.csv\"")
                .build();
    }
}
