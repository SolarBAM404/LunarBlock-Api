package com.lunardev.lunarlib.tasks.timers;

import com.lunardev.lunarlib.tasks.RunnableObject;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A repeated timer for an action
 */
public abstract class Timer extends RunnableObject {

    private final JavaPlugin plugin;
    private final int maxTime;
    protected int currentTime = 0;

    protected Timer(JavaPlugin plugin, int maxTime) {
        this.plugin = plugin;
        this.maxTime = maxTime;
    }

    @Override
    protected void action() {
        if (currentTime < maxTime) {
            secondAction();
        } else {
            cancelTask();
        }
        currentTime++;
    }

    public abstract void secondAction();

    public void startTimer() {
        this.startRepeating(plugin, 0, 1);
    }
}
