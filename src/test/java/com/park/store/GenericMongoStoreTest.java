package com.park.store;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by sharath on 26/1/16.
 */
@Ignore("ignore from build")
public class GenericMongoStoreTest {
    GenericMongoStore genericMongoStore;

    @Before
    public void setup() throws IOException {
        genericMongoStore = new GenericMongoStore("mongodb://parkice:parkservice@ds051585.mongolab.com:51585/spaces");
    }

    @Test
    public void testGetMongoClient() throws Exception {
        assertNotNull(genericMongoStore);
        assertEquals(genericMongoStore.createJongo().runCommand("{ping:1}").as(JsonNode.class).get("ok").asInt(), 1);
    }

    @After
    public void smash() {
        genericMongoStore.closeMongo();
    }
}