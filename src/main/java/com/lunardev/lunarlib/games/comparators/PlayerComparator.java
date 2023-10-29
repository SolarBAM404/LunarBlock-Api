package com.lunardev.lunarlib.games.comparators;

import com.lunardev.lunarlib.games.PlayerEntity;

import java.util.Comparator;

public class PlayerComparator implements Comparator<PlayerEntity> {
    @Override
    public int compare(PlayerEntity o1, PlayerEntity o2) {
        if (o1.getScore() > o2.getScore()) {
            return -1;
        } else if (o1.getScore() < o2.getScore()) {
            return 1;
        }
        return 0;
    }
}
