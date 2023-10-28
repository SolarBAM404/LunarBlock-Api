package com.lunardev.lunarlib.games.events;

import com.lunardev.lunarlib.games.GameStateManager;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Event executed just after timer finishes but before the game end event
 */
public class GameEndingEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter
    private final GameStateManager stateManager;

    public GameEndingEvent(GameStateManager manager) {
        this.stateManager = manager;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}
