package me.solar.lunarlib.commands;

import lombok.Data;
import lombok.NonNull;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Standard string type argument with or without an attach permission node
 */
@Data
public class CommandArgument {

    private @NonNull String value;
    private @Nullable String permissionNode;

    /**
     * Instantiates a new Command argument.
     *
     * @param value the value
     */
    public CommandArgument(@NotNull String value) {
        this.value = value;
        this.permissionNode = null;
    }

    /**
     * Instantiates a new Command argument.
     *
     * @param value          the value
     * @param permissionNode the permission node
     */
    public CommandArgument(@NotNull String value, @Nullable String permissionNode) {
        this.value = value;
        this.permissionNode = permissionNode;
    }

    /**
     * Test if an audience has a permission node or not.
     *
     * @param audience the audience
     * @return true if they have permission false otherwise
     */
    public boolean testPermission(Audience audience) {
        return CommandFramework.testPermission(audience, permissionNode);
    }
}
