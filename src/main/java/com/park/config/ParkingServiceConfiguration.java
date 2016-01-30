package com.park.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;
import com.park.store.GenericMongoStore;

import java.io.IOException;

/**
 * Created by sharath on 26/1/16.
 * <p/>
 * Parking Service Configuration file. Contains objects for YAML config sections.
 */
public class ParkingServiceConfiguration extends Configuration {
    public void setSpacesStore(GenericMongoStore spacesStore) {
        this.spacesStore = spacesStore;
    }

    /**
     * Spaces Store. The DB containing location and other info of parking spaces
     */
    @NotEmpty
    @JsonProperty("spaces.store")
    GenericMongoStore spacesStore;

    public void setMongoStore(String mongoURL) throws IOException {
        this.spacesStore = new GenericMongoStore(mongoURL);
    }

    public GenericMongoStore getMongoStore() {
        return spacesStore;
    }
}
