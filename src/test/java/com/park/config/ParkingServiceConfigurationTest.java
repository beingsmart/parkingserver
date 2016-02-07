package com.park.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Created by sharath on 26/1/16.
 */
public class ParkingServiceConfigurationTest {

    private ParkingServiceConfiguration parkingServiceConfiguration;
    YAMLFactory yf = new YAMLFactory();

    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = Jackson.newObjectMapper(yf);
        parkingServiceConfiguration = objectMapper.readValue(this.getClass().getClassLoader().getResource("parkingserverconfig.yml"),
                ParkingServiceConfiguration.class);
    }

    @Test
    public void testGetMongoStore() throws Exception {
        System.out.println(parkingServiceConfiguration.getMongoStore().toString());
        assertNotNull(parkingServiceConfiguration);
    }
}