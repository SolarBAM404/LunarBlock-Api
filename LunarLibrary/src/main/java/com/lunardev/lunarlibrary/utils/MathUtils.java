package com.lunardev.lunarlibrary.utils;

import org.bukkit.Location;

public final class MathUtils {

    // Location Maths

    public static Location addToLocation(Location loc, double x, double y, double z) {
        return new Location(loc.getWorld(), loc.getX() + x, loc.getY() + y, loc.getZ() + z);
    }

    public static Location subtractFromLocation(Location loc, double x, double y, double z) {
        return new Location(loc.getWorld(), loc.getX() - x, loc.getY() - y, loc.getZ() - z);
    }

    public static Location multiplyLocation(Location loc, double x, double y, double z) {
        return new Location(loc.getWorld(), loc.getX() * x, loc.getY() * y, loc.getZ() * z);
    }

    public static Location divideLocation(Location loc, double x, double y, double z) {
        return new Location(loc.getWorld(), loc.getX() / x, loc.getY() / y, loc.getZ() / z);
    }

    public static Location addToLocation(Location loc, Location loc2) {
        return new Location(loc.getWorld(), loc.getX() + loc2.getX(), loc.getY() + loc2.getY(), loc.getZ() + loc2.getZ());
    }

    public static Location subtractFromLocation(Location loc, Location loc2) {
        return new Location(loc.getWorld(), loc.getX() - loc2.getX(), loc.getY() - loc2.getY(), loc.getZ() - loc2.getZ());
    }

    public static Location multiplyLocation(Location loc, Location loc2) {
        return new Location(loc.getWorld(), loc.getX() * loc2.getX(), loc.getY() * loc2.getY(), loc.getZ() * loc2.getZ());
    }

    public static Location divideLocation(Location loc, Location loc2) {
        return new Location(loc.getWorld(), loc.getX() / loc2.getX(), loc.getY() / loc2.getY(), loc.getZ() / loc2.getZ());
    }

}
