package com.lunardev.lunarlib.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Supplier;

public class CommandBuilder {

    /**
     * The Cmd name.
     */
    private final String cmdName;
    /**
     * The Aliases.
     */
    private final List<String> aliases = new ArrayList<>();
    /**
     * The Arguments.
     */
    private final Dictionary<Integer, List<Object>> arguments = new Hashtable<>();
    /**
     * The Description.
     */
    private String description = "";
    /**
     * The Usage message.
     */
    private String usageMessage;
    private String permission;
    private Supplier<Boolean> action;
    private int argumentPosition = -1;

    public CommandBuilder(String cmdName) {
        this.cmdName = cmdName;
    }

    public CommandBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CommandBuilder withAliases(String... aliases) {
        this.aliases.addAll(Arrays.asList(aliases));
        return this;
    }

    public CommandBuilder withUsageMessage(String usageMessage) {
        this.usageMessage = usageMessage;
        return this;
    }

    public CommandBuilder withPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public CommandBuilder withArgsAtPosition(int position, Object... args) {
        arguments.put(position, List.of(args));
        return this;
    }

    public CommandBuilder withArgsPosition(int argumentPosition) {
        this.argumentPosition = argumentPosition;
        return this;
    }

    public CommandBuilder withAction(Supplier<Boolean> action) {
        this.action = action;
        return this;
    }

    public CommandFramework build() {
        return new CommandFramework(cmdName, description, aliases, usageMessage, permission, argumentPosition, arguments) {
            @Override
            public boolean execute(CommandSender sender, Command command, String commandLabel, String[] args) {
                return action.get();
            }
        };
    }

    public CommandFramework buildAndRegister(JavaPlugin plugin) {
        CommandFramework framework = this.build();
        framework.registerCommand(plugin);
        return framework;
    }

}
