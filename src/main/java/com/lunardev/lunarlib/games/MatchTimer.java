package com.lunardev.lunarlib.games;

import com.lunardev.lunarlib.games.events.GameEndingEvent;
import com.lunardev.lunarlib.games.events.GameStartingEvent;
import com.lunardev.lunarlib.tasks.RunnableObject;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A timer for the match
 */
public class MatchTimer extends RunnableObject {

    private final JavaPlugin plugin;
    private final GameStateManager manager;
    private final int gameTime;

    public MatchTimer(JavaPlugin plugin, GameStateManager manager, int gameTime) {
        this.plugin = plugin;
        this.manager = manager;
        this.gameTime = gameTime;
    }

    @Override
    public void action() {
        new GameEndingEvent(manager).callEvent();
        manager.endGame();
    }

    public void startGame() {
        new GameStartingEvent(manager).callEvent();
        this.startTask(plugin, gameTime);
    }
}
