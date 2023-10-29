package com.lunardev.lunarlib.games;

import com.lunardev.lunarlib.games.comparators.PlayerComparator;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

public class GamePlayerManager {

    private Dictionary<String, PlayerEntity> players;

    public void addPlayer(Player player) {
        players.put(player.getName(), new PlayerEntity(player.getName()));
    }

    public void removePlayer(Player player) {
        players.remove(player.getName());
    }

    public @Nullable PlayerEntity getPlayer(String playername) {
        return players.get(playername);
    }

    public List<PlayerEntity> getPlayerRanking(@Nullable Integer limit) {
        List<PlayerEntity> playerEntities = Collections.list(players.elements());
        playerEntities.sort(new PlayerComparator());
        return playerEntities.subList(0, limit != null ? limit : playerEntities.size());
    }

}
