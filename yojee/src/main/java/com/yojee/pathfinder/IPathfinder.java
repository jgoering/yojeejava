package com.yojee.pathfinder;

import com.yojee.data.Location;

import java.util.List;

public interface IPathfinder {
    List<Path> findPaths(List<Location> locations, Location distributionPoint, int nofCouriers);
}
