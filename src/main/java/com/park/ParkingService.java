package com.park;

import com.codahale.metrics.health.HealthCheck;
import com.park.config.ParkingServiceConfiguration;
import com.park.healthcheck.SpaceStoreHealthCheck;
import com.park.resources.Park;
import com.park.resources.Space;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.jongo.Jongo;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
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

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        Space space = new Space();
        Park park = new Park();
        environment.jersey().register(space);
        environment.jersey().register(park);
    }

    public static void main(String args[]) throws Exception {
        new ParkingService().run(args);
    }
}
