package com.yojee.pathfinder;

import com.yojee.data.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {
    private List<Location> locations = new ArrayList<>();

    public List<Location> getLocations() {
        return Collections.unmodifiableList(locations);
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public Location getLastLocation() {
        if (getLocations().size() == 0) {
            return null;
        }
        return getLocations().get(getLocations().size()-1);
    }

    public double getDistance() {
        double distance = 0;
        Location lastLocation = null;
        for (Location location:getLocations()) {
            if (lastLocation != null) {
                distance += lastLocation.getDistance(location);
            }
            lastLocation = location;
        }
        return distance;
    }
}
