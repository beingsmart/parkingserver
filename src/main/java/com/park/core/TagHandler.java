package com.park.core;

import com.park.ParkingService;
import com.park.api.TaggedLocation;
import com.park.config.constants.MongoFields;
import org.jongo.MongoCollection;


/**
 * Created by sarath on 07/02/16.
 */
public class TagHandler {
    private final String userId;
    private final MongoCollection parkedNow;

    public TagHandler(String userId) {
        this(userId, MongoFields.PARKED_NOW);

    }

    TagHandler(String userId, String collectionName) {
        this.userId = userId;
        parkedNow = ParkingService.jongo.getCollection(collectionName);

    }

    public void tagLocation(Double lon, Double lat) {
        Double[] coordinates = new Double[2];

        coordinates[0] = lon;
        coordinates[1] = lat;

        TaggedLocation location = new TaggedLocation();
        location.setCoordinates(coordinates);
        location.setUserId(userId);
        parkedNow.insert(location);
    }

    public TaggedLocation retrieveTag(String userId) {
        TaggedLocation as = parkedNow.findOne("{user_id:\"" + userId + "\"}").as(TaggedLocation.class);
        return as;
    }

    public void removeTag(String userId) {
        parkedNow.remove("{user_id:\"" + userId + "\"}");
    }
}
