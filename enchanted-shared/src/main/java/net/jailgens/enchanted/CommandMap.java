package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Optional;

/**
 * A high-level map of command labels to commands.
 *
 * @author Sparky983
 * @param <T> the type of the command this command map maps.
 */
public interface CommandMap<T extends @NotNull Command> {

    @Contract(value = "-> new", pure = true)
    static <T extends @NotNull Command> @NotNull CommandMap<@NotNull T> create() {

        return new CommandMapImpl<>();
    }

    /**
     * Registers the command.
     *
     * @param command the command.
     * @throws NullPointerException if the command is {@code null}.
     * @throws IllegalArgumentException if a command with one of the command's labels has already
     * been registered.
     */
    @Contract(mutates = "this")
    void registerCommand(final @NotNull T command);

    /**
     * Unregisters the command.
     *
     * @param command the command.
     * @throws IllegalArgumentException if
     */
    @Contract(mutates = "this")
    void unregisterCommand(final @NotNull T command);

    /**
     * Gets the command with the specified label.
     *
     * @param label the label.
     * @return an optional containing the command if it exists, otherwise {@code Optional.empty()}.
     * @throws NullPointerException if the label is {@code null}.
     */
    @NotNull Optional<? extends @NotNull T> getCommand(final @NotNull String label);

    /**
     * Gets all the registered commands.
     *
     * @return an unmodifiable collection of all the registered commands.
     */
    @NotNull @Unmodifiable Collection<? extends @NotNull T> getRegisteredCommands();
}
