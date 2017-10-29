package com.yojee.pathfinder;

import com.yojee.data.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PathTest {

    private static final Location NEW_YORK = new Location(40.7128, -74.0060);
    private static final Location LOS_ANGELES = new Location(34.0522, -118.2437);


    @Test
    void getDistance() {
        Path path = new Path();
        assertEquals(0, path.getDistance());
        path.addLocation(NEW_YORK);
        assertEquals(0, path.getDistance());
        path.addLocation(LOS_ANGELES);
        assertEquals(3935.746254609722,path.getDistance());
        path.addLocation(NEW_YORK);
        assertEquals(7871.492509219444,path.getDistance());
    }

    @Test
    void getLastLocation() {
        Path path = new Path();
        assertNull(path.getLastLocation());
        path.addLocation(NEW_YORK);
        assertEquals(NEW_YORK, path.getLastLocation());
        path.addLocation(LOS_ANGELES);
        assertEquals(LOS_ANGELES, path.getLastLocation());
    }
}