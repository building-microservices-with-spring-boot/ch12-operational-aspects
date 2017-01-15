package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class LocationService {

    private final CounterService counter;

    public static final Set<String> CITIES
            = new HashSet<String>(Arrays.asList("Berlin", "Budapest", "Dublin", "Kabul", "Nairobi"));

    @Autowired
    public LocationService(CounterService counter) {
        this.counter = counter;
    }

    public void checkLocation(String location) {
        if (location == null) {
            return;
        }

        if (CITIES.contains(location)) {
            counter.increment("user.location.listed");
        } else {
            counter.increment("user.location.notfound");
        }

    }
}
