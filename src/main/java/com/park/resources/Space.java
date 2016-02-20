package com.park.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Optional;
import com.park.api.ParkingSpace;
import com.park.api.SpaceList;
import com.park.config.constants.PathConstants;
import com.park.core.SpaceHandler;
import com.park.core.UploadHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by sharath on 26/1/16.
 * Space Resource. Handle all space related services
 */
@Path("/v1/space")
@Produces(MediaType.APPLICATION_JSON)

public class Space {

    public static final String SPACE = "_space";
    private final Logger logger;

    public Space() {
        logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * POST Request
     * Endpoint: /v1/space/bulk_upload/IND
     * Service to upload parking spaces to the spaces DB.
     *
     * @param country   Country of the parking space collection
     * @param spaceList List of parking spaces to be uploaded
     * @return Response responds whether the operation is success or not.
     */
    @POST
    @Path("/bulk_upload/{" + PathConstants.COUNTRY + "}")
    public Response upload(@PathParam(PathConstants.COUNTRY) String country, SpaceList spaceList) throws IOException {
        UploadHandler uploadHandler = new UploadHandler(country + SPACE);
        for (ParkingSpace parkingSpace : spaceList.getSpaces()) {
            uploadHandler.uploadSpace(parkingSpace);
        }
        return Response.ok().build();
    }

    @GET
    @Path("/near/lat/{" + PathConstants.LAT + "}/lng/{" + PathConstants.LON + "}")
    public JsonNode nearLocation(@PathParam(PathConstants.LAT) double lat,
                                 @PathParam(PathConstants.LON) double lon,
                                 @QueryParam(PathConstants.COUNTRY) Optional<String> country) throws JsonProcessingException {

        SpaceHandler spaceHandler = new SpaceHandler(country.or("ind") + SPACE, lat, lon);
        JsonNode spaceList = spaceHandler.findSpace();
        return spaceList;
    }
}
