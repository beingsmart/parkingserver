package com.park.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.park.ParkingService;
import com.park.api.TaggedLocation;
import events.EventType;
import log.ActivityInfo;
import log.Location;
import log.PlaceInfo;
import log.UserInfo;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sarath on 24/10/16.
 */
public class EventGenerator implements Runnable {
    private final TaggedLocation taggedLocation;
    private final String userId;
    private final EventType[] eventTypes;

    public EventGenerator(TaggedLocation taggedLocation, String userId, EventType... eventTypes) {

        this.taggedLocation = taggedLocation;
        this.userId = userId;
        this.eventTypes = eventTypes;
    }

    public void generateEvents() throws JsonProcessingException {
        DateTime now = DateTime.now();
        Double[] coordinates = taggedLocation.getCoordinates();
        Location location = null;

        if (coordinates.length > 0) {
            location = new Location(coordinates[1], coordinates[0]);
        }

        for (EventType eventType : eventTypes) {
            new ActivityInfo(userId, location, now, eventType).createEvent(ParkingService.jongo);
            switch (eventType) {
                case APP_OPEN:
                    //User activity aggregator
                    UserInfo userInfo = new UserInfo(now, userId);
                    userInfo.createEvent(ParkingService.jongo);
                    break;
                case PARKING:
                    // unique parking locations aggregator
                    uplEvent(now, location);
                    break;
                case VACATING:
                    // unique parking locations aggregator
                    uplEvent(now, location);
                    break;
                default:
                    System.out.println("Dont have a case for the eventType:" + eventType.name());
            }
        }
    }

    private void uplEvent(DateTime now, Location location) throws JsonProcessingException {
        if (location != null) {
            PlaceInfo placeInfo = new PlaceInfo(now, location);
            placeInfo.createEvent(ParkingService.jongo);
        }
    }

    @Override
    public void run() {
        try {
            generateEvents();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
