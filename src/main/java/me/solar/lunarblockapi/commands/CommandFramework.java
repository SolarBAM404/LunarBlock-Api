package me.solar.lunarblockapi.commands;

import lombok.Data;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

@Data
public abstract class CommandFramework implements TabExecutor {

    private String cmdName;
    private String description;
    private List<String> aliases;
    private String usageMessage;
    private @Nullable String permission;
    private Dictionary<Integer, List<CommandFramework>> subCommands;
    private Dictionary<Integer, List<CommandArgument>> arguments;

    protected CommandFramework(String cmdName) {
        this.cmdName = cmdName;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return execute(sender, command, label, args);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return tabComplete(sender, command, label, args);
    }

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

    public abstract boolean execute(CommandSender sender, Command command, String commandLabel, String[] args);

    public boolean testPermission(Audience audience) {
        if (permission != null) {
            if (audience instanceof Player player) {
                return player.hasPermission(permission);
            } else if (audience instanceof ConsoleCommandSender console) {
                return console.hasPermission(permission);
            }
        }
        return true;
    }
}
