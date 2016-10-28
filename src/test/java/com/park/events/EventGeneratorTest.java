package com.park.events;

import com.park.ParkingService;
import com.park.api.TaggedLocation;
import com.park.resources.Park;
import com.park.store.GenericMongoStore;
import events.EventType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by sarath on 26/10/16.
 */
public class EventGeneratorTest {

    private EventGenerator eventGenerator;
    private GenericMongoStore genericMongoStore;

    @Before
    public void setup() throws IOException {
        ParkingService.executor = Executors.newFixedThreadPool(1);
        System.setProperty("OPENSHIFT_MONGODB_DB_HOST", "OPENSHIFT_MONGODB_DB_HOST");
        System.setProperty("OPENSHIFT_MONGODB_DB_PORT", "OPENSHIFT_MONGODB_DB_PORT");

        genericMongoStore = new GenericMongoStore("mongodb://parkice:parkservice@ds051585.mongolab.com:51585/spaces");
        ParkingService.jongo = genericMongoStore.createJongo();
    }

    @Test
    public void testRun() throws Exception {


        Thread parentThread = new Thread(new Runnable() {
            @Override
            public void run() {
                TaggedLocation taggedLocation = new TaggedLocation();
                Double[] coordinates = new Double[2];
                coordinates[0] = 121.1111;
                coordinates[1] = 11.1122;
                taggedLocation.setCoordinates(coordinates);
                taggedLocation.setUserId("1adfadf111");
                eventGenerator = new EventGenerator(taggedLocation, "1adfadf111", EventType.PARKING);
                Thread thread = new Thread(eventGenerator);
                thread.setDaemon(true);
                thread.start();
            }
        });
        parentThread.start();
        parentThread.join();


        //ParkingService.executor.execute(eventGenerator);
//        ParkingService.executor.shutdown();
//        while (!ParkingService.executor.isTerminated()) {
//            System.out.println("waiting");
//            ParkingService.executor.awaitTermination(1000L, TimeUnit.MILLISECONDS);
//        }
    }

}