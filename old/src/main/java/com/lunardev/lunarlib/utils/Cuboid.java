package com.lunardev.lunarlib.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public final class Cuboid {
    final Random randomGenerator = new Random();
    private final Location highPoint;
    private final Location lowPoint;

    /**
     * Constructs a new cuboid from two corner points.
     *
     * @param startLoc the first point
     * @param endLoc   the second point
     */
    public Cuboid(final Location startLoc, final Location endLoc) {

        if (startLoc != null && endLoc != null) {
            final int lowx = Math.min(startLoc.getBlockX(), endLoc.getBlockX());
            final int lowy = Math.min(startLoc.getBlockY(), endLoc.getBlockY());
            final int lowz = Math.min(startLoc.getBlockZ(), endLoc.getBlockZ());

            final int highx = Math.max(startLoc.getBlockX(), endLoc.getBlockX());
            final int highy = Math.max(startLoc.getBlockY(), endLoc.getBlockY());
            final int highz = Math.max(startLoc.getBlockZ(), endLoc.getBlockZ());

            this.highPoint = new Location(startLoc.getWorld(), highx, highy, highz);
            this.lowPoint = new Location(startLoc.getWorld(), lowx, lowy, lowz);
        } else {
            this.highPoint = null;
            this.lowPoint = null;
        }

    }

    /**
     * Loads a cuboid from a Map representation.
     *
     * @param root the Map containing the cuboid data
     * @return the loaded cuboid
     * @throws IllegalArgumentException if the root map is invalid
     */
    public static Cuboid load(final Map<String, Object> root) throws IllegalArgumentException {
        if (root == null) {
            throw new IllegalArgumentException("Invalid root map!");
        }

        final World world = Bukkit.getServer().getWorld((String) root.get("World"));
        final int x1 = (Integer) root.get("X1");
        final int y1 = (Integer) root.get("Y1");
        final int z1 = (Integer) root.get("Z1");
        final int x2 = (Integer) root.get("X2");
        final int y2 = (Integer) root.get("Y2");
        final int z2 = (Integer) root.get("Z2");

        return new Cuboid(new Location(world, x1, y1, z1), new Location(world, x2, y2, z2));
    }

    /**
     * Checks if the specified cuboid is entirely contained within this cuboid.
     *
     * @param area the cuboid to check
     * @return true if the specified cuboid is within this cuboid, false otherwise
     */
    public boolean isAreaWithinArea(final Cuboid area) {
        return (this.containsLoc(area.highPoint) && this.containsLoc(area.lowPoint));
    }

    /**
     * Checks if the specified location is contained within this cuboid.
     *
     * @param loc the location to check
     * @return true if the location is within this cuboid, false otherwise
     */
    public boolean containsLoc(final Location loc) {
        if (this.highPoint == null || this.lowPoint == null) {
            return false;
        }
        if (loc == null || loc.getWorld() == null || !loc.getWorld().equals(this.highPoint.getWorld())) {
            return false;
        }

        return this.lowPoint.getBlockX() <= loc.getBlockX() && this.highPoint.getBlockX() >= loc.getBlockX() && this.lowPoint.getBlockZ() <= loc.getBlockZ()
                && this.highPoint.getBlockZ() >= loc.getBlockZ() && this.lowPoint.getBlockY() <= loc.getBlockY() && this.highPoint.getBlockY() >= loc.getBlockY();
    }

    /**
     * Determines whether the specified location is within the X and Z bounds of this cuboid,
     * ignoring the Y coordinate.
     *
     * @param loc the location to check
     * @return true if the location is within the X and Z bounds of this cuboid, false otherwise
     */
    public boolean containsLocWithoutY(final Location loc) {
        if (this.highPoint == null || this.lowPoint == null) {
            return false;
        }
        if (loc == null || loc.getWorld() == null || !loc.getWorld().equals(this.highPoint.getWorld())) {
            return false;
        }

        return this.lowPoint.getBlockX() <= loc.getBlockX() && this.highPoint.getBlockX() >= loc.getBlockX() && this.lowPoint.getBlockZ() <= loc.getBlockZ()
                && this.highPoint.getBlockZ() >= loc.getBlockZ();
    }

    /**
     * Determines whether the specified location is within the X and Z bounds of this cuboid,
     * ignoring the Y coordinate.
     *
     * @param loc the location to check
     * @return true if the location is within the X and Z bounds of this cuboid, false otherwise
     */
    public boolean containsLocWithoutYD(final Location loc) {
        if (this.highPoint == null || this.lowPoint == null) {
            return false;
        }
        if (loc == null || loc.getWorld() == null || !loc.getWorld().equals(this.highPoint.getWorld())) {
            return false;
        }

        return this.lowPoint.getBlockX() <= loc.getBlockX() + 2 && this.highPoint.getBlockX() >= loc.getBlockX() - 2
                && this.lowPoint.getBlockZ() <= loc.getBlockZ() + 2 && this.highPoint.getBlockZ() >= loc.getBlockZ() - 2;
    }

    /**
     * Returns the volume of this cuboid.
     *
     * @return the volume of this cuboid
     */
    public long getSize() {
        return Math.abs(this.getXSize() * this.getYSize() * this.getZSize());
    }

    /**
     * Determines a random location inside the cuboid and returns it.
     *
     * @return a random location within the cuboid
     */
    public Location getRandomLocation() {
        final World world = this.getWorld();

        Location result = new Location(world, this.highPoint.getBlockX(), this.highPoint.getBlockY(), this.highPoint.getZ());

        if (this.getSize() > 1) {
            final double randomX = this.lowPoint.getBlockX() + randomGenerator.nextDouble(this.getXSize());
            final double randomY = this.lowPoint.getBlockY() + randomGenerator.nextDouble(this.getYSize());
            final double randomZ = this.lowPoint.getBlockZ() + randomGenerator.nextDouble(this.getZSize());

            result = new Location(world, randomX, randomY, randomZ);
        }

        return result;
    }

    /**
     * Determines a random location inside the cuboid that is suitable for mob spawning and returns it.
     *
     * @return a random location inside the cuboid that is suitable for mob spawning
     */
    public Location getRandomLocationForMobs() {
        final Location temp = this.getRandomLocation();

        return new Location(temp.getWorld(), temp.getBlockX() + 0.5, temp.getBlockY() + 0.5, temp.getBlockZ() + 0.5);
    }

    /**
     * Returns the x span of this cuboid.
     *
     * @return the x span of this cuboid
     */
    public int getXSize() {
        return (this.highPoint.getBlockX() - this.lowPoint.getBlockX()) + 1;
    }

    /**
     * Returns the y span of this cuboid.
     *
     * @return the y span of this cuboid
     */
    public int getYSize() {
        return (this.highPoint.getBlockY() - this.lowPoint.getBlockY()) + 1;
    }

    /**
     * Returns the z span of this cuboid.
     *
     * @return the z span of this cuboid
     */
    public int getZSize() {
        return (this.highPoint.getBlockZ() - this.lowPoint.getBlockZ()) + 1;
    }

    /**
     * Returns the higher location of this cuboid.
     *
     * @return the higher location of this cuboid
     */
    public Location getHighLoc() {
        return this.highPoint;
    }

    /**
     * Returns the lower location of this cuboid.
     *
     * @return the lower location of this cuboid
     */
    public Location getLowLoc() {
        return this.lowPoint;
    }

    /**
     * Returns the world this cuboid is in.
     *
     * @return the world this cuboid is in
     */
    public World getWorld() {
        return this.highPoint.getWorld();
    }

    /**
     * Saves this cuboid to a Map representation.
     *
     * @return the Map containing the cuboid data
     */
    public Map<String, Object> save() {
        final Map<String, Object> root = new LinkedHashMap<>();

        root.put("World", this.highPoint.getWorld().getName());
        root.put("X1", this.highPoint.getBlockX());
        root.put("Y1", this.highPoint.getBlockY());
        root.put("Z1", this.highPoint.getBlockZ());
        root.put("X2", this.lowPoint.getBlockX());
        root.put("Y2", this.lowPoint.getBlockY());
        root.put("Z2", this.lowPoint.getBlockZ());

        return root;
    }

    /**
     * Returns a string representation of this cuboid, showing the corner points.
     *
     * @return the string representation of this cuboid
     */
    @Override
    public String toString() {
        return "(" + this.lowPoint.getBlockX() + ", " + this.lowPoint.getBlockY() + ", " + this.lowPoint.getBlockZ() + ") to (" +
                this.highPoint.getBlockX() + ", " + this.highPoint.getBlockY() + ", " + this.highPoint.getBlockZ() + ")";
    }

    /**
     * Returns a raw string representation that is easy to read for Java.
     *
     * @return a raw representation of this cuboid
     */
    public String toRaw() {
        return this.getWorld().getName() + "," + this.lowPoint.getBlockX() + "," + this.lowPoint.getBlockY() + "," + this.lowPoint.getBlockZ() +
                "," + this.highPoint.getBlockX() + "," + this.highPoint.getBlockY() + "," + this.highPoint.getBlockZ();
    }
}
