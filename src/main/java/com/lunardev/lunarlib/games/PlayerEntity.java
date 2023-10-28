package com.lunardev.lunarlib.games;

import lombok.Data;

@Data
public class PlayerEntity {

    private String playerName;
    private int score = 0;

    public PlayerEntity(String playerName) {
        this.playerName = playerName;
    }

    public void addScore(int points) {
        score += points;
    }

    public void removeScore(int points) {
        score -= points;
    }
}
