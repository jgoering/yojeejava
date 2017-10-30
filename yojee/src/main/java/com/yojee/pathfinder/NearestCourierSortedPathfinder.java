package com.yojee.pathfinder;

import com.yojee.data.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* iterates over locations, picks the next closest courier */
public class NearestCourierSortedPathfinder implements IPathfinder {

    /* used to sort location by distance to distribution point. */
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

    private boolean balance;

    public NearestCourierSortedPathfinder() {
    }

    /** @param balance the paths of the couriers as a second step */
    public NearestCourierSortedPathfinder(boolean balance) {
        this.balance = balance;
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
        if (balance) {
            balance(result);
        }
        return result;
    }

    /** repeatedly finds unused couriers and moves part of the longest path to them */
    static void balance(List<Path> paths) {
        //calculate ideal number of hops for one courier
        int totalhops = 0;
        for (Path path : paths) {
            totalhops += path.getLocations().size();
        }
        int target = totalhops / paths.size();

        Path unused = findUnusedCouriers(paths);
        while (unused != null) {
            Path largestPath = findLargestPath(paths);
            splitpath(largestPath, unused, target);
            unused = findUnusedCouriers(paths);
        }
    }

    /**
     * moves part of the largestPath to the unused path
     * @param target how many hops a courier should ideally have.
     */
    static void splitpath(Path largestPath, Path empty, int target) {
        if (largestPath == null) {
            throw new IllegalArgumentException("Largest path can't be null");
        }
        if (target < 2) {
            throw new IllegalArgumentException("target must be larger than 2");
        }
        if (largestPath.getLocations().size() < target) {
            //largest path already shorter than target
            return;
        }
        if (largestPath.getLocations().size() < 3) {
            //nothing to split
            return;
        }
        if (empty == null) {
            throw new IllegalArgumentException("Parameter 'empty' should not be null");
        }
        while (largestPath.getLocations().size() > target) {
            empty.addLocation(largestPath.getLocations().remove(target));
        }
    }

    /** returns the largest path in the given list. */
    static Path findLargestPath(List<Path> paths) {
        if (paths == null || paths.size() == 0) {
            return null;
        }
        Path max = new Path();
        for (Path path : paths) {
            if (path.getLocations().size() >= max.getLocations().size()) {
                max = path;
            }
        }
        return max;
    }

    /** returns an empty path (no location besides distribution point), or null if no empty path exists. */
    static Path findUnusedCouriers(List<Path> paths) {
        if (paths == null) {
            return null;
        }
        for (Path path : paths) {
            if (path.getLocations().size() <= 1) {
                return path;
            }
        }
        return null;
    }
}
