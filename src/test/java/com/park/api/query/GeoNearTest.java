package com.park.api.query;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sharath on 29/1/16.
 */
@Ignore("ignore from build")
public class GeoNearTest {

    private GeoNear geoNear;

    @Before
    public void setup() {
        Double[] coordinates = new Double[2];
        coordinates[0] = 66.1334;
        coordinates[1] = 12.227;
        geoNear = new GeoNear("INDSPACES", coordinates);
    }

    @Test
    public void testQuery() throws Exception {
        assertEquals(geoNear.limit(1).query(), "{\"geoNear\":\"INDSPACES\",\"near\":{\"type\":\"point\",\"coordinates\":[66.1334,12.227]},\"spherical\":true,\"limit\":1}");
    }
}