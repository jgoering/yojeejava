package com.yojee.data;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocationDataSource {
    private final static List<Location> LOCATIONS = new ArrayList<>();
    private final static Location DISTRIBUTION_POINT = new Location (11.552931,104.933636);

    public static List<Location> getLocations() {
        return Collections.unmodifiableList(LOCATIONS);
    }
    public static Location getDistributionPoint() { return DISTRIBUTION_POINT;}


    public static void init() throws IOException {
        LOCATIONS.clear();
        String csvFile = "locations.csv";
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource(csvFile).getInputStream()))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(cvsSplitBy);
                try {
                    Location location = new Location(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
                    if (location.getLatitude() < 11 || location.getLatitude() > 12 || location.getLongitude() < 104 || location.getLongitude() > 106) {
                        //assuming faulty data
                        System.err.println("Ignoring: " + location);
                    } else {
                        LOCATIONS.add(location);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Ignoring: " + line);
                }
            }
        }
    }

    /* returns a (hypothetical) location showing the minimum latitude and longitude from the location list */
    public static Location getMinValues() {
        double latitude = 9999;
        double longitude = 9999;
        for (Location location:getLocations()) {
            if (location.getLatitude() < latitude) {
                latitude = location.getLatitude();
            }
            if (location.getLongitude() < longitude) {
                longitude = location.getLongitude();
            }
        }
        return new Location(latitude, longitude);
    }

    /* returns a (hypothetical) location showing the maximum latitude and longitude from the location list */
    public static Location getMaxValues() {
        double latitude = -9999.0;
        double longitude = -9999.0;
        for (Location location:getLocations()) {
            if (location.getLatitude() > latitude) {
                latitude = location.getLatitude();
            }
            if (location.getLongitude() > longitude) {
                longitude = location.getLongitude();
            }
        }
        return new Location(latitude, longitude);
    }
}
