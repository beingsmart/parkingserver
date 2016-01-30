package com.park.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by sharath on 29/1/16.
 */
public class GeoJson {
    private ObjectNode geoJson;
    private ObjectMapper objectMapper;

    public GeoJson(ParkingSpace parkingSpace) {
        objectMapper = new ObjectMapper();
        geoJson = objectMapper.createObjectNode();
        geoJson.putPOJO("loc", parkingSpace);
    }

    public ObjectNode getGeoJson() {
        return geoJson;
    }
}
