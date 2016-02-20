package com.park.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.park.api.TaggedLocation;
import com.park.config.constants.PathConstants;
import com.park.core.TagHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by sarath on 07/02/16.
 */
@Path("/v1/park")
@Produces(MediaType.APPLICATION_JSON)
public class Park {

    private ObjectMapper objectMapper;

    public Park() {
        objectMapper = new ObjectMapper();
    }

    @GET
    @Path("/at/lat/{" + PathConstants.LAT + "}/lng/{" + PathConstants.LON + "}/for/{" + PathConstants.USER_ID + "}")
    public JsonNode parkNow(@PathParam(PathConstants.LAT) double lat,
                                @PathParam(PathConstants.LON) double lon,
                                @PathParam(PathConstants.USER_ID) String userId) throws JsonProcessingException {
        TagHandler tagHandler = new TagHandler(userId);
        tagHandler.tagLocation(lon, lat);
        return objectMapper.createObjectNode().put("ok", 1);
    }

    @GET
    @Path("/locate/for/{" + PathConstants.USER_ID + "}")
    public TaggedLocation findThing(@PathParam(PathConstants.USER_ID) String userId){
        TagHandler tagHandler = new TagHandler(userId);
        TaggedLocation taggedLocation =tagHandler.retrieveTag(userId);
        return taggedLocation;
    }

    @GET
    @Path("/vacate/for/{" + PathConstants.USER_ID + "}")
    public JsonNode vacate(@PathParam(PathConstants.USER_ID) String userId){
        TagHandler tagHandler = new TagHandler(userId);
        tagHandler.removeTag(userId);
        return objectMapper.createObjectNode().put("ok", 1);
    }
}
