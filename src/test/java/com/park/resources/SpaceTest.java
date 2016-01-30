package com.park.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.park.ParkingService;
import com.park.api.ParkingSpace;
import com.park.api.SpaceList;
import com.park.store.GenericMongoStore;
import org.jongo.Jongo;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by sharath on 29/1/16.
 */
@Ignore()
public class SpaceTest {

    private Space space;
    private ObjectMapper objectMapper;
    private GenericMongoStore genericMongoStore;

    @Before
    public void setUp() throws Exception {
        genericMongoStore = new GenericMongoStore("mongodb://parkice:parkservice@ds051585.mongolab.com:51585/spaces");
        ParkingService.jongo = genericMongoStore.createJongo();
        space = new Space();
        objectMapper = new ObjectMapper();
    }

    @After
    public void tearDown() throws Exception {
        genericMongoStore.closeMongo();
    }

    @Test
    public void testUpload() throws Exception {
        String spaceListInSagar = this.getClass().getClassLoader().getResource("sagarspaces.json").getFile();
        SpaceList spaceList = objectMapper.readValue(new File(spaceListInSagar), SpaceList.class);
        space.upload("ind", spaceList);
    }

    @Test
    public void testNearLocation() throws JsonProcessingException {
        Optional<String> x = Optional.of("ind");
        System.out.println(space.nearLocation(16.604008, 79.304321, x));
    }
}