package com.lunardev.lunarlib.tasks.timers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class TitleTimer extends Timer {

    private final List<Player> players;

    public TitleTimer(JavaPlugin plugin, int maxTime, List<Player> players) {
        super(plugin, maxTime);
        this.players = players;
    }

    @Override
    public void secondAction() {
        for (Player player : players) {
            player.sendTitlePart(TitlePart.TITLE, Component.text(currentTime));
        }
    }

    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
}
