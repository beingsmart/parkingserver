package com.park.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 * Created by sharath on 26/1/16.
 */
public class ParkingServiceConfigurationTest {

    private ParkingServiceConfiguration parkingServiceConfiguration;
    YAMLFactory yf = new YAMLFactory();
    private URL url;

    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = Jackson.newObjectMapper(yf);
        parkingServiceConfiguration = objectMapper.readValue(this.getClass().getClassLoader().getResource("parkingserverconfig.yml"),
                ParkingServiceConfiguration.class);
    }

    @Test
    public void testGetMongoStore() throws Exception {
        System.setProperty("OPENSHIFT_MONGODB_DB_HOST", "ds051585.mongolab.com");
        System.setProperty("OPENSHIFT_MONGODB_DB_PORT", "51585");

        System.out.println(parkingServiceConfiguration.getMongoStore().toString());
        assertNotNull(parkingServiceConfiguration);
    }
}