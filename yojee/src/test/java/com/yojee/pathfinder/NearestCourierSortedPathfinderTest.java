package com.yojee.pathfinder;

import com.yojee.data.Location;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NearestCourierSortedPathfinderTest {

    private static final Location NEW_YORK = new Location(40.7128, -74.0060);
    private static final Location DISTRIBUTION_POINT = new Location(11.552931,104.933636);
    private static final Location LOS_ANGELES = new Location(34.0522, -118.2437);

    @Test
    void findPathsNoCourier() {
        try {
            new NearestCourierSortedPathfinder().findPaths(null, new Location(0,0), 0);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //all good
        }
    }

    @Test
    void findPathsNoDistributionPoint() {
        try {
            new NearestCourierSortedPathfinder().findPaths(null, null, 15);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //all good
        }
    }

    @Test
    void findPathNullLocations() {
        List<Path> paths = new NearestCourierSortedPathfinder().findPaths(null, DISTRIBUTION_POINT, 1);
        assertNotNull(paths);
        assertEquals(1, paths.size());
        assertEquals(1, paths.get(0).getLocations().size());
        assertEquals(DISTRIBUTION_POINT, paths.get(0).getLocations().get(0));
    }

    @Test
    void findPathNoLocations() {
        List<Location> locations = new ArrayList<>();
        List<Path> paths = new NearestCourierSortedPathfinder().findPaths(locations, DISTRIBUTION_POINT, 1);
        assertNotNull(paths);
        assertEquals(1, paths.size());
        assertEquals(1, paths.get(0).getLocations().size());
        assertEquals(DISTRIBUTION_POINT, paths.get(0).getLocations().get(0));
    }

    @Test
    void findPathOneLocationOneCourier() {
        List<Location> locations = new ArrayList<>();
        locations.add(NEW_YORK);
        List<Path> paths = new NearestCourierSortedPathfinder().findPaths(locations, DISTRIBUTION_POINT, 1);
        assertNotNull(paths);
        assertEquals(1, paths.size());
        assertEquals(2, paths.get(0).getLocations().size());
        assertEquals(DISTRIBUTION_POINT, paths.get(0).getLocations().get(0));
        assertEquals(NEW_YORK, paths.get(0).getLocations().get(1));
    }

    @Test
    void findPathOneLocationTwoCouriers() {
        List<Location> locations = new ArrayList<>();
        locations.add(NEW_YORK);
        List<Path> paths = new NearestCourierSortedPathfinder().findPaths(locations, DISTRIBUTION_POINT, 2);
        assertNotNull(paths);
        assertEquals(2, paths.size());
        assertEquals(2, paths.get(0).getLocations().size());
        assertEquals(1, paths.get(1).getLocations().size());
        assertEquals(DISTRIBUTION_POINT, paths.get(0).getLocations().get(0));
        assertEquals(NEW_YORK, paths.get(0).getLocations().get(1));
    }

    @Test
    void findPathTwoLocationsOneCourier() {
        List<Location> locations = new ArrayList<>();
        locations.add(NEW_YORK);
        locations.add(LOS_ANGELES);
        List<Path> paths = new NearestCourierSortedPathfinder().findPaths(locations, DISTRIBUTION_POINT, 1);
        assertNotNull(paths);
        assertEquals(1, paths.size());
        assertEquals(3, paths.get(0).getLocations().size());
        assertEquals(DISTRIBUTION_POINT, paths.get(0).getLocations().get(0));
        assertEquals(LOS_ANGELES, paths.get(0).getLocations().get(1));
        assertEquals(NEW_YORK, paths.get(0).getLocations().get(2));
    }

    @Test
    void findPathTwoLocationsTwoCouriers() {
        List<Location> locations = new ArrayList<>();
        locations.add(NEW_YORK);
        locations.add(LOS_ANGELES);
        List<Path> paths = new NearestCourierSortedPathfinder().findPaths(locations, DISTRIBUTION_POINT, 2);
        assertNotNull(paths);
        assertEquals(2, paths.size());
        assertEquals(3, paths.get(0).getLocations().size());
        assertEquals(1, paths.get(1).getLocations().size());
        assertEquals(DISTRIBUTION_POINT, paths.get(0).getLocations().get(0));
        assertEquals(LOS_ANGELES, paths.get(0).getLocations().get(1));
        assertEquals(NEW_YORK, paths.get(0).getLocations().get(2));
        assertEquals(DISTRIBUTION_POINT, paths.get(1).getLocations().get(0));
    }
}