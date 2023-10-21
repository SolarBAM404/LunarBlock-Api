package me.solar.lunarblockapi.commands;

import lombok.Data;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * A command framework to support both normal string arguments and subcommands
 */
@Data
public abstract class CommandFramework implements TabExecutor {

    /**
     * The Cmd name.
     */
    protected String cmdName;
    /**
     * The Description.
     */
    protected String description;
    /**
     * The Aliases.
     */
    protected List<String> aliases;
    /**
     * The Usage message.
     */
    protected String usageMessage;
    /**
     * The Permission.
     */
    protected @Nullable String permission;
    /**
     * The Sub commands.
     */
    protected Dictionary<Integer, List<CommandFramework>> subCommands;
    /**
     * The Arguments.
     */
    protected Dictionary<Integer, List<CommandArgument>> arguments;

    /**
     * Test if an audience has a permission node or not.
     *
     * @param audience   the audience
     * @param permission the permission
     * @return true if they have permission false otherwise
     */
    public static boolean testPermission(Audience audience, String permission) {
        if (permission != null) {
            if (audience instanceof Player player) {
                return player.hasPermission(permission);
            } else if (audience instanceof ConsoleCommandSender console) {
                return console.hasPermission(permission);
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return tabComplete(sender, command, label, args);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (testPermission(sender)) {
            return execute(sender, command, label, args);
        }
        return false;
    }

    /**
     * Tab complete list.
     *
     * @param sender  the sender
     * @param command the command
     * @param label   the label
     * @param args    the args
     * @return the list
     */
    public List<String> tabComplete(CommandSender sender, Command command, String label, @NotNull String[] args) {
        String arg = args[args.length - 1];
        List<String> tabList = new ArrayList<>();

        // Subcommand Args
        List<CommandFramework> subCommandArgs = subCommands.get(args.length);

        if (subCommandArgs != null) {
            for (CommandFramework subArg : subCommandArgs) {
                if (subArg.getCmdName().startsWith(arg) && subArg.testPermission(sender)) {
                    tabList.add(subArg.getCmdName());
                }
            }
        }

        // Value Args
        List<CommandArgument> argValues = arguments.get(args.length);

        if (argValues != null) {
            for (CommandArgument argument : argValues) {
                if (argument.getValue().startsWith(arg) && argument.testPermission(sender)) {
                    tabList.add(argument.getValue());
                }
            }
        }

        return tabList;
    }

    /**
     * The execution of the command
     *
     * @param sender       Source of the command
     * @param command      The command that was executed
     * @param commandLabel The aliases used to execute the command
     * @param args         The arguments used in the command
     * @return true if valid command otherwise false
     */
    public abstract boolean execute(CommandSender sender, Command command, String commandLabel, String[] args);

    /**
     * Test if an audience has a permission node or not.
     *
     * @param audience the audience
     * @return true if they have permission false otherwise
     */
    public boolean testPermission(Audience audience) {
        return CommandFramework.testPermission(audience, permission);
    }

    /**
     * Register command.
     *
     * @param plugin the plugin
     */
    public void registerCommand(JavaPlugin plugin) {
        CommandManager.registerCommand(plugin, this);
    }

    /**
     * Unregister command.
     *
     * @param plugin the plugin
     */
    public void unregisterCommand(JavaPlugin plugin) {
        CommandManager.unregisterCommand(plugin, cmdName);
    }
}
