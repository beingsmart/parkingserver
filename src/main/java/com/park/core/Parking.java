package com.park.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.park.ParkingService;
import com.park.api.ParkingSpace;
import com.park.api.query.GeoNear;
import org.hibernate.validator.constraints.Email;
import org.jongo.MongoCollection;

import java.util.List;

/**
 * Created by sharath on 29/1/16.
 */
public class Parking {
    private String collectionName;
    private final Double[] coordinates = new Double[2];

    public Parking(String collectionName, double lat, double lon) {
        this.collectionName = collectionName;
        coordinates[0] = lon;
        coordinates[1] = lat;
    }

    public JsonNode findSpace() throws JsonProcessingException {
        ObjectNode nearBy = new ObjectMapper().createObjectNode();
        GeoNear geoNear = new GeoNear(collectionName, coordinates);
        String geoNearQuery = geoNear.maxDistance(2000).query();
        List<JsonNode> results = ParkingService.jongo.runCommand(geoNearQuery).field("results").as(JsonNode.class);
        int count = 0;
        for (JsonNode geoJson : results) {
            JsonNode obj = geoJson.get("obj");
            JsonNode loc = obj.get("loc");
            nearBy.put("lng", loc.get("coordinates").get(0).asDouble());
            nearBy.put("lat", loc.get("coordinates").get(1).asDouble());
            nearBy.put("name", loc.get("spaceName").asText());
            nearBy.put("id", count++);
        }
        return nearBy;
    }
}
