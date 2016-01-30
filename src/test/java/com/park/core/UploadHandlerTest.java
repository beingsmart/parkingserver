package com.park.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.park.ParkingService;
import com.park.api.ParkingSpace;
import com.park.store.GenericMongoStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by sharath on 29/1/16.
 */
public class UploadHandlerTest {

    public static final String PLACES = "places";
    private UploadHandler places;
    private ObjectMapper objectMapper;
    private GenericMongoStore genericMongoStore;

    @Before
    public void setup() throws IOException {
        genericMongoStore = new GenericMongoStore("mongodb://parkice:parkservice@ds051585.mongolab.com:51585/spaces");
        ParkingService.jongo = genericMongoStore.createJongo();
        ParkingService.jongo.getCollection(PLACES).remove();
        objectMapper = new ObjectMapper();
        places = new UploadHandler(PLACES);
    }

    @Test
    public void testUploadSpace() throws Exception {
        ParkingSpace parkingSpace = objectMapper.readValue("{\"coordinates\":[63.667,12.1225], " +
                "\"type\":\"Point\", \"spaceName\":\"My space\"}", ParkingSpace.class);
        ParkingSpace parkingSpaceWithIn10Meters = objectMapper.readValue("{\"coordinates\":[63.6679,12.1225], " +
                "\"type\":\"Point\", \"spaceName\":\"My space\"}", ParkingSpace.class);
        assertEquals(places.uploadSpace(parkingSpace), "new");
        assertEquals(places.uploadSpace(parkingSpace), "exists");
        assertEquals(places.uploadSpace(parkingSpaceWithIn10Meters), "exists");
    }

    @After
    public void smash() {
        ParkingService.jongo.getCollection(PLACES).remove();
        genericMongoStore.closeMongo();

    }
}