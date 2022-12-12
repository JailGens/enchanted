package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Adapts enchanted-paper commands to paper commands, so they can be used by the
 * {@link org.bukkit.command.CommandMap}.
 *
 * @author Sparky983
 */
interface CommandMapCommandAdapter {

    /**
     * Adapts a {@link PaperCommand} to a {@link org.bukkit.command.Command}.
     *
     * @param command the command.
     * @return the paper command.
     * @throws NullPointerException if the command is {@code null}.
     */
    @Contract(value = "_ -> new", pure = true)
    org.bukkit.command.@NotNull Command adapt(@NotNull PaperCommand command);
}
