package me.solar.lunarblockapi.commands;

import lombok.Data;
import lombok.NonNull;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
public class CommandArgument {

    private @NonNull String value;
    private @Nullable String permissionNode;

    public CommandArgument(@NotNull String value) {
        this.value = value;
        this.permissionNode = null;
    }

    public CommandArgument(@NotNull String value, @Nullable String permissionNode) {
        this.value = value;
        this.permissionNode = permissionNode;
    }

    public boolean testPermission(Audience audience) {
        if (permissionNode != null) {
            if (audience instanceof Player player) {
                return player.hasPermission(permissionNode);
            } else if (audience instanceof ConsoleCommandSender console) {
                return console.hasPermission(permissionNode);
            }
        }
        return true;
    }
}
