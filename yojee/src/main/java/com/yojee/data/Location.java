package com.yojee.data;

import java.util.List;

public class Location {
    private final double latitude;
    private final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    /**
     * from https://github.com/jasonwinn/haversine/blob/master/Haversine.java
     */
    public double getDistance(Location location) {

        if (location == null) {
            return Double.MAX_VALUE;
        }

        double dLat  = Math.toRadians((location.latitude - this.latitude));
        double dLong = Math.toRadians((location.longitude - this.longitude));

        double startLat = Math.toRadians(this.latitude);
        double endLat   = Math.toRadians(location.latitude);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- d
    }

    private static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    /* finds nearest neighbor from a list of locations */
    public Location findNearestNeighbor(List<Location> locations) {
        Location result = null;
        double shortestDistance = Double.MAX_VALUE;
        for (Location location: locations) {
            double distance = location.getDistance(this);
            if (distance < shortestDistance) {
                shortestDistance = distance;
                result = location;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return Double.compare(location.latitude, latitude) == 0 && Double.compare(location.longitude, longitude) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
