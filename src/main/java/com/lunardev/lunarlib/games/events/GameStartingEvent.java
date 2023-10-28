package com.lunardev.lunarlib.games.events;

import com.lunardev.lunarlib.games.GameStateManager;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event executed when game is ready to start.
 * <p/>
 * Essentially when enough players are in the lobby for a game to start
 */
public class GameStartingEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter
    private final GameStateManager stateManager;

    public GameStartingEvent(GameStateManager manager) {
        this.stateManager = manager;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
