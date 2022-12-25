package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A command factory that turns user-defined annotated commands into usable
 * {@link net.jailgens.enchanted.Command}s.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface CommandFactory {

    /**
     * Creates a command from the specified user-defined command.
     *
     * @param command the user-defined command.
     * @return the created command.
     * @throws IllegalArgumentException if the command failed validation.
     * @throws NullPointerException if the command is {@code null}.
     * @since 0.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    @NotNull CommandGroup createCommand(@NotNull Object command);
}
