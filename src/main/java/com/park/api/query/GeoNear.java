package com.park.api.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by sharath on 27/1/16.
 * GeoNear Query creator.
 * In Alpha, It only accepts a point.
 */
public class GeoNear {

    private final ObjectMapper objectMapper;
    private final ObjectNode geoNear;
    private final String collectionName;
    private final Double[] coordinates;

    public GeoNear(String collectionName, Double[] coordinates) {
        this.collectionName = collectionName;
        this.coordinates = coordinates;
        objectMapper = new ObjectMapper();
        geoNear = objectMapper.createObjectNode();
        setQuery();
    }

    private void setQuery() {
        geoNear.put("geoNear", collectionName);
        ObjectNode near = geoNear.with("near");
        near.put("type", "Point");
        ArrayNode coordinates = near.withArray("coordinates");
        for (Double coordinate : this.coordinates) {
            coordinates.add(coordinate);
        }
        geoNear.put("spherical", true);
    }

    public GeoNear limit(int limit) {
        geoNear.put("limit", limit);
        return this;
    }

    public GeoNear geoNearQuery() {
        return this;
    }

    public String query() throws JsonProcessingException {
        return objectMapper.writeValueAsString(geoNear);
    }

    public GeoNear maxDistance(int maxDistance) {
        geoNear.put("maxDistance", maxDistance);
        return this;
    }
}
