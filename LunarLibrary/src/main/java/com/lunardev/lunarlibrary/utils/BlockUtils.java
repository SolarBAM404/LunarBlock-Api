package com.lunardev.lunarlibrary.utils;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import lombok.Getter;

public class BlockUtils {

    /**
     * This class is used to define a cuboid region in a 3D space.
     * It provides methods to get minimum and maximum points, the center, and the size of the cuboid.
     * It also provides methods to check if a given location is inside the cuboid using different types of locations, entity or blocks.
     */
    @Getter
    public static class Cuboid {
        private Location loc1;
        private Location loc2;

        /**
         * Constructs a new Cuboid with the given locations as corners.
         *
         * @param loc1 One corner of the cuboid.
         * @param loc2 The opposite corner of the cuboid.
         */
        public Cuboid(Location loc1, Location loc2) {
            this.loc1 = loc1;
            this.loc2 = loc2;
        }

        /**
         * Returns the minimum point of the cuboid.
         *
         * @return The minimum point of the cuboid.
         */
        public Location getMin() {
            return new Location(loc1.getWorld(), Math.min(loc1.getX(), loc2.getX()), Math.min(loc1.getY(), loc2.getY()), Math.min(loc1.getZ(), loc2.getZ()));
        }

        /**
        * Returns the maximum point of the cuboid.
        *
        * @return The maximum point of the cuboid.
        */
        public Location getMax() {
            return new Location(loc1.getWorld(), Math.max(loc1.getX(), loc2.getX()), Math.max(loc1.getY(), loc2.getY()), Math.max(loc1.getZ(), loc2.getZ()));
        }

        /**
        * Returns the center point of the cuboid.
        *
        * @return The center point of the cuboid.
        */
        public Location getCenter() {
            return new Location(loc1.getWorld(), (loc1.getX() + loc2.getX()) / 2, (loc1.getY() + loc2.getY()) / 2, (loc1.getZ() + loc2.getZ()) / 2);
        }

        /**
        * Returns the size of the cuboid.
        *
        * @return The size of the cuboid as a vector.
        */
        public Vector getSize() {
            return new Vector(Math.abs(loc1.getX() - loc2.getX()), Math.abs(loc1.getY() - loc2.getY()), Math.abs(loc1.getZ() - loc2.getZ()));
        }

        /**
         * Checks if the given cuboid is inside this cuboid.
         *
         * @param cuboid The cuboid to check.
         * @return True if the given cuboid is inside this cuboid, false otherwise.
         */
        public boolean isInside(Cuboid cuboid) {
            return isInside(cuboid.getMin()) && isInside(cuboid.getMax());
        }

        /**
         * Checks if the given location is inside this cuboid.
         *
         * @param location The location to check.
         * @return True if the given location is inside this cuboid, false otherwise.
         */
        public boolean isInside(Location location) {
            return isInside(location.toVector());
        }

        /**
         * Checks if the given vector is inside this cuboid.
         *
         * @param vector The vector to check.
         * @return True if the given vector is inside this cuboid, false otherwise.
         */
        public boolean isInside(Vector vector) {
            return isInside(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
        }

        /**
         * Checks if the given x,y,z is inside this cuboid.
         *
         * @param x The x coordiate to check.
         * @param y The y coordiate to check.
         * @param z The z coordiate to check.
         * @return True if the given x,y,z is inside this cuboid, false otherwise.
         */
        public boolean isInside(int x, int y, int z) {
            return x >= getMin().getBlockX() && x <= getMax().getBlockX() && y >= getMin().getBlockY() && y <= getMax().getBlockY() && z >= getMin().getBlockZ() && z <= getMax().getBlockZ();
        }

        /**
         * Checks if the given block is inside this cuboid.
         *
         * @param block The cuboid to check.
         * @return True if the given block is inside this cuboid, false otherwise.
         */
        public boolean isInside(Block block) {
            return isInside(block.getLocation());
        }

        /**
         * Checks if the given entity is inside this cuboid.
         *
         * @param entity The entity to check.
         * @return True if the given entity is inside this cuboid, false otherwise.
         */
        public boolean isInside(Entity entity) {
            return isInside(entity.getLocation());
        }
        
    }

}
