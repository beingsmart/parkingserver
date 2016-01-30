package com.park.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.park.ParkingService;
import com.park.api.GeoJson;
import com.park.api.ParkingSpace;
import com.park.api.query.GeoNear;
import com.park.config.constants.MongoFields;
import org.jongo.MongoCollection;

import java.io.IOException;
import java.util.List;

/**
 * Created by sharath on 27/1/16.
 * UploadHandler : to upload spaces data to the collection <COUNTRY>_spaces
 */
public class UploadHandler {

    /**
     * MongoCollection created out of the collectionName @this got initialized
     */
    private final MongoCollection spaceColl;
    private final String spaceCollName;

    /**
     * @param spaceCollName Initialize MongoCollection with the given collectionName
     */
    public UploadHandler(String spaceCollName) {
        this.spaceCollName = spaceCollName;
        checkForIndex();
        spaceColl = ParkingService.jongo.getCollection(spaceCollName);
    }

    /**
     * <p/>
     * Upload the parking space location to the spaces db.
     * If the uploaded space is with in 10 sq meters of any existing space do not upload.
     * This is the sanity check in Alpha.
     *
     * @param parkingSpace ParkingSpace to be uploaded
     * @return status of the upload
     */
    public String uploadSpace(ParkingSpace parkingSpace) throws IOException {
        GeoNear geoNear = new GeoNear(spaceCollName, parkingSpace.getCoordinates());
        String geoNearQuery = geoNear.limit(1).query();
        List<JsonNode> results = ParkingService.jongo.runCommand(geoNearQuery).field("results").as(JsonNode.class);
        if (results != null && !results.isEmpty() && isLesserDistance(results.get(0).get("dis").asDouble(), 100d)) {
            return "exists";
        }
        spaceColl.insert(new GeoJson(parkingSpace).getGeoJson());
        return "new";
    }

    private void checkForIndex() {
        if (!ParkingService.jongo.getDatabase().getCollectionNames().contains(spaceCollName)) {
            ParkingService.jongo.getCollection(spaceCollName).ensureIndex("{loc:\"2dsphere\"}");
        }
    }

    boolean isLesserDistance(double dis, double measure) {
        return new Double(dis).compareTo(measure) < 1;
    }
}
