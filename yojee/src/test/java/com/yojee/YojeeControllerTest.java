package com.yojee;

import com.yojee.data.Location;
import com.yojee.pathfinder.Path;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class YojeeControllerTest {
    @Test
    void convertToLines() {
        List<Path> paths = new ArrayList<>();
        Path path = new Path();
        path.addLocation(new Location(1,2));
        path.addLocation(new Location(3, 4));
        paths.add(path);
        List<String> result = YojeeController.convertToLines(paths, 0, 0, 1, 1);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("2,1 4,3", result.get(0));
    }

}