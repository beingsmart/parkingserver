package com.park.core;

import com.mongodb.DuplicateKeyException;
import com.park.ParkingService;
import com.park.store.GenericMongoStore;
import org.jongo.Jongo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by sarath on 07/02/16.
 */
public class TagHandlerTest {

    public static final String PARKED_NOW_TEST = "parked_now_test";
    private static final double DELTA = 1e-15;

    private TagHandler tagHandler;
    private GenericMongoStore genericMongoStore;

    @Before
    public void setup() throws IOException {
        genericMongoStore = new GenericMongoStore("mongodb://parkice:parkservice@ds051585.mongolab.com:51585/spaces");
        ParkingService.jongo = genericMongoStore.createJongo();
        ParkingService.jongo.getCollection(PARKED_NOW_TEST).ensureIndex("{\"user_id\": 1}", "{unique: true}");
        tagHandler = new TagHandler("1", PARKED_NOW_TEST);
    }

    @Test(expected = com.mongodb.DuplicateKeyException.class)
    public void testTagLocation() throws Exception {
        tagHandler.tagLocation(1.9, 2.9);
        tagHandler.tagLocation(1.9, 2.9);
    }

    @Test
    public void testRetrieveTag() throws Exception {
        assertThat(tagHandler.retrieveTag("1").getCoordinates()[1], equalTo(2.9));
    }

    @Test
    public void testRemoveTag() throws Exception {
        tagHandler.removeTag();
        assertNull(tagHandler.retrieveTag("1"));
    }

    @After
    public void tearDown(){
        //ParkingService.jongo.getCollection(PARKED_NOW_TEST).drop();
        genericMongoStore.closeMongo();
    }
}