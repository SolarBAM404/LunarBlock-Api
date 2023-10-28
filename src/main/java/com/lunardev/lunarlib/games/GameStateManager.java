package com.lunardev.lunarlib.games;

import com.lunardev.lunarlib.games.events.GameEndEvent;
import com.lunardev.lunarlib.games.events.GameStartEvent;
import com.lunardev.lunarlib.tasks.RunnableObject;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

@Data
public class GameStateManager implements Listener {

    private JavaPlugin plugin;
    private GameStates state;
    private Arena arena;
    private GamePlayerManager playerManager;
    private RunnableObject matchTimer;


    private boolean proxyMode = false;

    public GameStateManager(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void startGame() {
        state = GameStates.PLAYING;
        new GameStartEvent(this).callEvent();
    }


    public void endGame() {
        new GameEndEvent(this).callEvent();
        state = GameStates.ENDED;
    }

    public void onPlayerJoin(PlayerJoinEvent event) {
        if (proxyMode && event.getPlayer().hasPermission("lunarlib.games.join.bypass")) {
            playerManager.addPlayer(event.getPlayer());
        }
    }
}
}