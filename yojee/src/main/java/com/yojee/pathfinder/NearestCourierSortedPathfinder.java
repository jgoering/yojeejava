package com.yojee.pathfinder;

import com.yojee.data.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* iterates over locations, picks the next closest courier */
public class NearestCourierSortedPathfinder implements IPathfinder {

    private class LocationHolder implements Comparable<LocationHolder>{
        private Location location;
        private double distance;

        LocationHolder(Location source, Location location) {
            this.location = location;
            distance = source.getDistance(location);
        }

        @Override
        public int compareTo(LocationHolder o) {
            return Double.compare(this.distance, o.distance);
        }
    }

    @Override
    public List<Path> findPaths(List<Location> locations, Location distributionPoint, int nofCouriers) {
        if (nofCouriers < 1) {
            throw new IllegalArgumentException("need at least one courier");
        }
        if (distributionPoint == null) {
            throw new IllegalArgumentException("no distribution point");
        }
        List<Path> result = new ArrayList<>();
        for (int i = 0; i < nofCouriers; i++) {
            result.add(new Path());
            result.get(i).addLocation(distributionPoint);
        }
        if (locations != null) {
            List<LocationHolder> todo = new ArrayList<>();
            for (Location location : locations) {
                todo.add(new LocationHolder(distributionPoint, location));
            }
            Collections.sort(todo);
            for (LocationHolder locationHolder : todo) {
                Location location = locationHolder.location;
                double shortestDistance = Double.MAX_VALUE;
                int nearestCourier = -1;
                for (int i = 0; i < nofCouriers; i++) {
                    double distance = result.get(i).getLastLocation().getDistance(location);
                    if (distance < shortestDistance) {
                        shortestDistance = distance;
                        nearestCourier = i;
                    }
                }
                result.get(nearestCourier).addLocation(location);
            }
        }
        return result;
    }
}
