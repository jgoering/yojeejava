package com.yojee;

import com.yojee.data.Location;
import com.yojee.data.LocationDataSource;
import com.yojee.pathfinder.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class YojeeController {

    @RequestMapping("/")
    public String home(@RequestParam(required = false) String algorithm, Map<String, Object> model) {
        int width = 1080;
        int height = 500;
        int nofCouriers = 16;

        //store some data in our model
        Location minValues = LocationDataSource.getMinValues();
        Location maxValues = LocationDataSource.getMaxValues();
        List<Location> locations = LocationDataSource.getLocations();
        Location distributionPoint = LocationDataSource.getDistributionPoint();
        double minx = minValues.getLongitude();
        double miny = minValues.getLatitude();
        double factorx = width / (maxValues.getLongitude() - minx);
        double factory = height / (maxValues.getLatitude() - miny);

        model.put("locations", locations);
        model.put("distributionPoint", distributionPoint);
        model.put("width", width);
        model.put("height", height);
        model.put("minx", minx);
        model.put("miny", miny);
        model.put("factorx", factorx);
        model.put("factory", factory);

        //pick the right algorithm
        IPathfinder pathfinder;
        if ("NearestCourier".equals(algorithm)) {
            pathfinder = new NearestCourierPathfinder();
        } else if ("NearestCourierSorted".equals(algorithm)) {
            pathfinder = new NearestCourierSortedPathfinder();
        } else {
            pathfinder = new NearestNeighborPathfinder();
        }
        model.put("algorithm", pathfinder.getClass().getSimpleName());

        //run the pathfinder
        long start = System.currentTimeMillis();
        List<Path> paths = pathfinder.findPaths(locations, distributionPoint, nofCouriers);
        model.put("time", System.currentTimeMillis()-start);

        //store results
        double total = 0;
        for (Path path : paths) {
            total += path.getDistance();
        }
        model.put("totalDistance", total);
        model.put("paths", paths);
        List<String> lines = convertToLines(paths, minx, miny, factorx, factory);
        model.put("lines", lines);

        return "index";
    }

    /** converts a list of paths to a string representing the points for a line suitable for an SVG polyline element. */
    static List<String> convertToLines(List<Path> paths, double minx, double miny, double factorx, double factory) {
        List<String> result = new ArrayList<>();
        for (Path path : paths) {
            StringBuilder sb = new StringBuilder();
            for (Location location : path.getLocations()) {
                sb.append((int) ((location.getLongitude() - minx) * factorx)).append(",").append((int) ((location.getLatitude() - miny) * factory)).append(" ");
            }
            sb.delete(sb.length()-1, sb.length());
            result.add(sb.toString());
        }
        return result;
    }

}