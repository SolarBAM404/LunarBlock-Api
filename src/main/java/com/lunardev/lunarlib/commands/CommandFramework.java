package com.lunardev.lunarlib.commands;

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

import java.util.*;

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

    protected int argumentPosition = -1;

    /**
     * The Arguments.
     */
    protected Dictionary<Integer, List<Object>> arguments;

    protected CommandFramework(String cmdName) {
        this(cmdName, null);
    }

    protected CommandFramework(String cmdName, String description) {
        this(cmdName, description, null);
    }

    protected CommandFramework(String cmdName, String description, List<String> aliases) {
        this(cmdName, description, aliases, null);
    }

    protected CommandFramework(String cmdName, String description, List<String> aliases, String usageMessage) {
        this(cmdName, description, aliases, null, null);
    }

    protected CommandFramework(String cmdName, String description, List<String> aliases, String usageMessage, @Nullable String permission) {
        this(cmdName, description, aliases, usageMessage, null, null);
    }

    protected CommandFramework(@NotNull String cmdName, @Nullable String description, @Nullable List<String> aliases, @Nullable String usageMessage, @Nullable String permission, @Nullable int argumentPosition) {
        this(cmdName, description, aliases, usageMessage, permission, -1, null);
    }

    protected CommandFramework(String cmdName, String description, List<String> aliases, String usageMessage, @Nullable String permission, Dictionary<Integer, List<Object>> arguments) {
        this(cmdName, description, aliases, usageMessage, permission, -1, arguments);
    }

    protected CommandFramework(@NotNull String cmdName, @Nullable String description, @Nullable List<String> aliases,
                               @Nullable String usageMessage, @Nullable String permission,
                               int argumentPosition, @Nullable Dictionary<Integer, List<Object>> arguments) {
        this.cmdName = cmdName;
        this.description = description;
        this.aliases = aliases;
        this.usageMessage = usageMessage;
        this.permission = permission;
        this.argumentPosition = argumentPosition;
        this.arguments = arguments;
    }

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
            int i = 0;
            for (String arg : args) {
                if (isSubCommand(arg)) {
                    String[] newArgs = new String[args.length - (i + 1)];
                    for (int j = 0; j + i < args.length; j++) {
                        newArgs[j] = args[j + i];
                    }
                    return Objects.requireNonNull(getSubCommand(arg)).onCommand(sender, command, label, newArgs);

                }

                if (isCommandArg(arg)
                        && !Objects.requireNonNull(getCommandArg(arg)).testPermission(sender)) {
                    return false;
                }
                i++;
            }

            return execute(sender, command, label, args);
        }

        return false;
    }

    private CommandArgument getCommandArg(String arg) {
        Enumeration<List<Object>> args = this.arguments.elements();
        while (args.hasMoreElements()) {
            List<Object> argList = args.nextElement();
            for (Object argument : argList) {
                if (argument instanceof CommandArgument cmdArgument
                        && Objects.equals(cmdArgument.getValue(), arg)) {
                    return cmdArgument;
                }
            }
        }
        return null;
    }

    private boolean isCommandArg(String arg) {
        return getCommandArg(arg) != null;
    }

    private CommandFramework getSubCommand(String cmdName) {
        Enumeration<List<Object>> args = this.arguments.elements();
        while (args.hasMoreElements()) {
            List<Object> argList = args.nextElement();
            for (Object argument : argList) {
                if (argument instanceof CommandFramework cmdFramework
                        && Objects.equals(cmdFramework.getCmdName(), cmdName)) {
                    return cmdFramework;
                }
            }
        }

        return null;
    }

    private boolean isSubCommand(String cmdName) {
        return getSubCommand(cmdName) != null;
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
        List<String> tabList = new ArrayList<>();

        // Value Args
        List<Object> argValues = arguments.get(args.length);

        if (args.length > 1 && isSubCommand(args[args.length - 2])) {
            argValues.addAll(Objects.requireNonNull(getSubCommand(cmdName))
                    .arguments.get(args.length - (args.length - 1)));
        }

        if (argValues != null) {

            for (Object argument : argValues) {
                if (argument instanceof CommandFramework cmdFramework) {
                    tabList.add(cmdFramework.getCmdName());
                } else if (argument instanceof CommandArgument cmdArgument) {
                    tabList.add(cmdArgument.getValue());
                } else if (argument instanceof String cmdString) {
                    tabList.add(cmdString);
                } else {
                    tabList.add(argument.toString());
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
