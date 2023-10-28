package com.lunardev.lunarlib.games;

import org.bukkit.entity.Player;

import java.util.Dictionary;

public class GamePlayerManager {

    private Dictionary<String, PlayerEntity> players;

    public void addPlayer(Player player) {
        players.put(player.getName(), new PlayerEntity(player.getName()));
    }

    public void removePlayer(Player player) {
        players.remove(player.getName());
    }
}
