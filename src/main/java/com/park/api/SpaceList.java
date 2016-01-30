package com.park.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by sharath on 27/1/16.
 * List of ParkingSpace s to be uploaded.
 */
public class SpaceList {
    SpaceList() {

    }

    /**
     * List of ParkingSpace s
     */
    @JsonProperty("spaces")
    private List<ParkingSpace> parkingSpaces;

    public List<ParkingSpace> getSpaces() {
        return parkingSpaces;
    }

    public void setSpaces(List<ParkingSpace> coordinates) {
        this.parkingSpaces = coordinates;
    }
}
