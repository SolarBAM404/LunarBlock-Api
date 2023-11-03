package com.lunardev.lunarlib.tasks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class RunnableObject {

    private int taskId = -1;

    protected abstract void action();

    private Runnable getRunnable() {
        return this::action;
    }

    public void startTask(JavaPlugin plugin, long delay) {
        plugin.getServer().getScheduler()
                .scheduleSyncDelayedTask(plugin, getRunnable(), delay * 20);
    }

    public void startRepeating(JavaPlugin plugin, long initialDelay, long repeatingDelay) {
        if (taskId == -1) {
            taskId = plugin.getServer().getScheduler()
                    .scheduleSyncRepeatingTask(plugin, getRunnable(), initialDelay, repeatingDelay);
        }
    }

    public void cancelTask() {
        if (taskId != -1) {
            Bukkit.getServer().getScheduler().cancelTask(taskId);
            taskId = -1;
        }
    }

}
