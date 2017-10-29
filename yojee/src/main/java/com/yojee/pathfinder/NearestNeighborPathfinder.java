package com.yojee.pathfinder;

import com.yojee.data.Location;

import java.util.ArrayList;
import java.util.List;

/* iterates over couriers, picks the next closest location */
public class NearestNeighborPathfinder implements IPathfinder {
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
            int currentCourier = 0;
            while (todo.size() > 0) {
                Path currentPath = result.get(currentCourier);
                Location next = findNearestNeighbor(todo, currentPath.getLastLocation());
                if (!todo.remove(next)) {
                    throw new IllegalStateException("findNearestNeighbor returned an element that is not part of the list of locations");
                }
                currentPath.addLocation(next);
                currentCourier++;
                currentCourier %= nofCouriers;
            }
        }
        return result;
    }

    Location findNearestNeighbor(List<Location> locations, Location lastLocation) {
        Location result = null;
        double shortestDistance = Double.MAX_VALUE;
        for (Location location: locations) {
            double distance = location.getDistance(lastLocation);
            if (distance < shortestDistance) {
                shortestDistance = distance;
                result = location;
            }
        }
        return result;
    }
}
