package com.park;

import com.codahale.metrics.health.HealthCheck;
import com.park.config.ParkingServiceConfiguration;
import com.park.healthcheck.SpaceStoreHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.jongo.Jongo;

import java.util.SortedMap;

/**
 * Created by sharath on 26/1/16.
 */
public class ParkingService extends Application<ParkingServiceConfiguration> {

    public static Jongo jongo;

    @Override
    public void run(ParkingServiceConfiguration parkingServiceConfiguration, Environment environment) throws Exception {

        SpaceStoreHealthCheck hc = new SpaceStoreHealthCheck(parkingServiceConfiguration.getMongoStore());
        environment.healthChecks().register("spaces.store", hc);
        SortedMap<String, HealthCheck.Result> healthChecks = environment.healthChecks().runHealthChecks();

        for (String entry : healthChecks.keySet()) {
            if (!healthChecks.get(entry).isHealthy()) {
                throw new Exception(entry + " can not be initialized", healthChecks.get(entry).getError());
            }
        }

        jongo = parkingServiceConfiguration.getMongoStore().build(environment);
    }

    public static void main(String args[]) throws Exception {
        new ParkingService().run(args);
    }
}
