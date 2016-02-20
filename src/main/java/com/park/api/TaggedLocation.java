package com.park.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoId;

/**
 * Created by sarath on 07/02/16.
 */
public class TaggedLocation {
    @MongoId
    public ObjectId _id;
    /**
     * coordinates of the tagged location.
     * Format: [lon,lat]
     */
    @JsonProperty("coordinates")
    private Double[] coordinates;

    /**
     * userId
     */
    @JsonProperty("userId")
    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public String getUserId() {
        return userId;
    }
}
