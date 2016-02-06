package com.park.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.park.ParkingService;
import com.park.api.ParkingSpace;
import com.park.store.GenericMongoStore;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by sharath on 29/1/16.
 */
@Ignore("ignore from build")
public class ParkingTest {

    private Parking places;
    private GenericMongoStore genericMongoStore;

    @Before
    public void setup() throws IOException {
        genericMongoStore = new GenericMongoStore("mongodb://parkice:parkservice@ds051585.mongolab.com:51585/spaces");
        ParkingService.jongo = genericMongoStore.createJongo();
        ObjectMapper objectMapper = new ObjectMapper();

        UploadHandler uploader = new UploadHandler("places");

        ParkingSpace parkingSpace = objectMapper.readValue("{\"coordinates\":[79.301553, 16.602784], \"type\":\"Point\", \"spaceName\":\"Sivaalayam\"}", ParkingSpace.class);
        uploader.uploadSpace(parkingSpace);

        parkingSpace = objectMapper.readValue("{\"coordinates\":[79.306347, 16.604003],  \"type\":\"Point\", \"spaceName\":\"Kamala Nehru Hospital\"}", ParkingSpace.class);
        uploader.uploadSpace(parkingSpace);
        places = new Parking("places", 16.604008, 79.304321);

    }

    @After
    public void smash() {
        ParkingService.jongo.getCollection("places").remove();
        genericMongoStore.closeMongo();
    }

    @Test
    public void testFindSpace() throws Exception {
        System.out.println(places.findSpace());
    }
}