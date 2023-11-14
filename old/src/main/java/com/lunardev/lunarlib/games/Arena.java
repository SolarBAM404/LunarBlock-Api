package com.lunardev.lunarlib.games;

import com.lunardev.lunarlib.utils.Cuboid;
import lombok.Data;
import org.bukkit.Location;

/**
 * Represents an arena in a game, containing details such as its name,
 * spawn points, lobbies, and player count limits. Additionally, it
 * specifies certain effects like daylight cycle and PvP (Player vs Player)
 * allowance within the arena.
 * <p>
 * The {@code @Data} annotation from Lombok will generate
 * getter, setter, toString, equals, and hashCode methods for all fields.
 */
@Data
public class Arena {

    /**
     * The internal name of the arena, used for identification purposes.
     */
    private String name;

    /**
     * The display name of the arena, presented to players.
     */
    private String displayName;

    /**
     * A cuboid region within the arena where players can spawn.
     */
    private Cuboid spawnPoints;

    /**
     * The location of the main lobby where players gather initially.
     */
    private Location mainLobby;

    /**
     * The location of the waiting lobby where players wait before entering the arena.
     */
    private Location waitingLobby;

    /**
     * The maximum number of players that can participate in the arena at one time.
     */
    private int maxPlayers;

    /**
     * The minimum number of players required to start a game in the arena.
     */
    private int minPlayers;

    /**
     * Specifies whether the daylight cycle should be active in the arena.
     */
    private boolean doDaylightCycle = false;

    /**
     * Specifies whether Player vs Player (PvP) combat is allowed in the arena.
     */
    private boolean pvpEnabled = false;

    /**
     * Specifies the default time of day (in ticks) for the arena.
     * A full day in Minecraft is 24000 ticks, with 0 representing sunrise,
     * 6000 midday, 12000 sunset, and 18000 midnight.
     */
    private int defaultTimeOfDay;

    /**
     * Represents the physical bounds of the arena in the world, defined as a 3D cuboid region.
     */
    private Cuboid area;

    public Location getSpawnPoint() {
        return spawnPoints.getRandomLocationForMobs();
    }
}
