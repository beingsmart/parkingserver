package com.park.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;

import java.util.List;

/**
 * Created by sharath on 27/1/16.
 * <p/>
 * ParkingSpace, Mongo Document of the Parking space.
 * In Alfa stage, we are dealing with only points
 */
public class ParkingSpace {

    @MongoId
    public ObjectId _id;
    /**
     * coordinates of the parking space.
     * Format: [lon,lat]
     */
    @JsonProperty("coordinates")
    private Double[] coordinates;

    /**
     * Parking space name
     */
    @JsonProperty("spaceName")
    private String spaceName;

    @JsonProperty("type")
    private String type;

    ParkingSpace() {

    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

