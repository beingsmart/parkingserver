package com.park.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by sharath on 29/1/16.
 */
public class ParkingSpaceTest {

    ParkingSpace parkingSpace;

    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        parkingSpace = objectMapper.readValue("{\"coordinates\":[63.667,12.1225], \"spaceName\":\"My space\"}", ParkingSpace.class);
    }

    @Test
    public void testGetSpaceName() throws Exception {
        assertEquals(parkingSpace.getSpaceName(), "My space");
    }
}