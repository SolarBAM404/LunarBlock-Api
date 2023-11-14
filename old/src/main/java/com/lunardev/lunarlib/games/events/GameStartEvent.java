package com.lunardev.lunarlib.games.events;

import com.lunardev.lunarlib.games.GameStateManager;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event executed when game has actually started
 */
public class GameStartEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();


    @Getter
    private final GameStateManager stateManager;

    public GameStartEvent(GameStateManager manager) {
        this.stateManager = manager;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
