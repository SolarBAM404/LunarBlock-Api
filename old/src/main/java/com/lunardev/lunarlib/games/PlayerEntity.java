package com.lunardev.lunarlib.games;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Data
public class PlayerEntity {

    private String playerName;
    private Player player;
    private int score = 0;

    public PlayerEntity(String playerName) {
        this.playerName = playerName;
        player = getPlayer();
    }

    public void addScore(int points) {
        score += points;
    }

    public void removeScore(int points) {
        score -= points;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerName);
    }
}
