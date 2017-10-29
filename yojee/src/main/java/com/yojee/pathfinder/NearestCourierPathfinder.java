package com.yojee.pathfinder;

import com.yojee.data.Location;

import java.util.ArrayList;
import java.util.List;

/* iterates over locations, picks the next closest courier */
public class NearestCourierPathfinder implements IPathfinder {
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
            List<Location> todo = new ArrayList<>(locations);
            for (Location location : todo) {
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
