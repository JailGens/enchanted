package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Optional;

public interface CommandMap {

    @Contract(value = "-> new", pure = true)
    static @NotNull CommandMap create() {

        return new CommandMapImpl();
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
    void registerCommand(final @NotNull Command command);

    /**
     * Unregisters the command.
     *
     * @param command the command.
     * @throws IllegalArgumentException if
     */
    @Contract(mutates = "this")
    void unregisterCommand(final @NotNull Command command);

    /**
     * Gets the command with the specified label.
     *
     * @param label the label.
     * @return an optional containing the command if it exists, otherwise {@code Optional.empty()}.
     * @throws NullPointerException if the label is {@code null}.
     */
    @NotNull Optional<? extends @NotNull Command> getCommand(final @NotNull String label);

    /**
     * Gets all the registered commands.
     *
     * @return an unmodifiable collection of all the registered commands.
     */
    @NotNull @Unmodifiable Collection<? extends @NotNull Command> getRegisteredCommands();
}
