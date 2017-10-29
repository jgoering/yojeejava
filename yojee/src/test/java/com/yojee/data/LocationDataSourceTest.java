package com.yojee.data;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LocationDataSourceTest {
    @Test
    void getMinValues() throws IOException {
        LocationDataSource.init();
        assertEquals(11.4523739, LocationDataSource.getMinValues().getLatitude());
        assertEquals(104.7079825, LocationDataSource.getMinValues().getLongitude());
    }

    @Test
    void getMaxValues() throws IOException {
        LocationDataSource.init();
        assertEquals(11.6938439, LocationDataSource.getMaxValues().getLatitude());
        assertEquals(105.008719, LocationDataSource.getMaxValues().getLongitude());
    }

    @Test
    void init() throws IOException {
        LocationDataSource.init();
        assertEquals(1598, LocationDataSource.getLocations().size());
        assertTrue(LocationDataSource.getLocations().contains(new Location(11.576722, 104.917455)));
        assertTrue(LocationDataSource.getLocations().contains(new Location(11.5355859, 104.9246927)));
        assertTrue(LocationDataSource.getLocations().contains(new Location(11.5722193, 104.8980058)));
        assertFalse(LocationDataSource.getLocations().contains(new Location(33.7789681, -118.1898225)));
    }

}