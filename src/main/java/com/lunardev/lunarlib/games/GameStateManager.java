package com.lunardev.lunarlib.games;

import com.lunardev.lunarlib.games.events.GameEndEvent;
import com.lunardev.lunarlib.games.events.GameStartEvent;
import com.lunardev.lunarlib.tasks.RunnableObject;
import lombok.Data;

@Data
public class GameStateManager {

    private GameStates state;
    private Arena arena;

    private RunnableObject matchTimer;

    public void startGame() {
        state = GameStates.PLAYING;
        new GameStartEvent(this).callEvent();
    }


    public void endGame() {
        new GameEndEvent(this).callEvent();
        state = GameStates.ENDED;
    }
}