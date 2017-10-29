package com.yojee.data;

import com.yojee.pathfinder.NearestNeighborPathfinder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {

    @Test
    void distanceWithSame() {
        Location start = new Location(0,0);
        Location end = new Location(0, 0);
        assertEquals(0,start.getDistance(end));
    }

    @Test
    void distanceToOtherSide() {
        Location start = new Location(90,0);
        Location end = new Location(-90, 0);
        assertEquals(20015.086796020572,start.getDistance(end));
    }
    @Test
    void distanceNewYorkLosAngeles() {
        Location start = new Location(40.7128, -74.0060);
        Location end = new Location(34.0522, -118.2437);
        assertEquals(3935.746254609722,start.getDistance(end));
    }

    @Test
    void distanceToNull() {
        assertEquals(Double.MAX_VALUE, new Location(0,0).getDistance(null));
    }

    @Test
    void findNearestNeighborTest(){
        List<Location> locations = new ArrayList<>();
        Location near = new Location(5, 5);
        Location far = new Location(10, 10);
        locations.add(near);
        locations.add(far);
        assertEquals(near, new Location(0,0).findNearestNeighbor(locations));
    }
}