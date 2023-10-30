package com.lunardev.lunarlib.commands;

import com.lunardev.lunarlib.exceptions.NoCommandNameProvidedException;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Static command manager
 */
public final class CommandManager {

    CommandManager() {
    }

    /**
     * Register command.
     *
     * @param plugin    the plugin
     * @param framework the framework
     */
    @SneakyThrows
    public static void registerCommand(@NotNull JavaPlugin plugin, @NotNull CommandFramework framework) {
        PluginCommand command = plugin.getCommand(framework.getCmdName());

        if (command == null) {
            if (framework.cmdName == null) {
                throw new NoCommandNameProvidedException("Command name not provided in the framework, " + framework.getClass());
            }
            Command cmd = new Command(
                    framework.getCmdName(),
                    framework.getDescription() != null ? framework.description : "",
                    framework.getUsageMessage() != null ? framework.usageMessage : "",
                    framework.getAliases() != null ? framework.aliases : new ArrayList<>()
            ) {
                @Override
                public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                    return framework.execute(sender, this, commandLabel, args);
                }
            };
            cmd.setPermission(framework.getPermission());
            plugin.getServer().getCommandMap().register(plugin.getPluginMeta().getName(), cmd);
        } else {
            command.setExecutor(framework);
        }
    }

    /**
     * Unregister command.
     *
     * @param plugin  the plugin
     * @param cmdName the cmd name
     */
    public static void unregisterCommand(JavaPlugin plugin, String cmdName) {
        try {
            PluginCommand command = plugin.getCommand(cmdName);

            if (command != null) {
                command.unregister(plugin.getServer().getCommandMap());
            }
        } catch (Exception e) {
            // ignored
        }
    }

}
