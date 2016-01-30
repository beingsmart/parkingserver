package com.park.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.fasterxml.jackson.databind.JsonNode;
import org.jongo.Jongo;
import com.park.store.GenericMongoStore;

/**
 * Created by sharath on 26/1/16.
 * Check SpacesStore before running the service
 */
public class SpaceStoreHealthCheck extends HealthCheck {
    private final GenericMongoStore jongoStore;

    public SpaceStoreHealthCheck(GenericMongoStore mongoStore) {
        this.jongoStore = mongoStore;
    }

    /**
     * Ping the mongodb and respond appropriately
     *
     * @return HealthCheck.Result
     * @throws Exception
     */
    @Override
    protected Result check() throws Exception {
        try {
            Jongo jongo = jongoStore.createJongo();
            jongo.runCommand("{ping:1}").as(JsonNode.class);
        } catch (Exception e) {
            return Result.unhealthy(e);
        } finally {
            jongoStore.closeMongo();
        }
        return Result.healthy();
    }
}
