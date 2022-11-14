package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A map of command labels to commands.
 *
 * @author Sparky983
 */
public final class CommandMap {

    private final @NotNull Map<@NotNull String, @NotNull Command> commands = new HashMap<>();

    /**
     * Registers the command.
     *
     * @param command the command.
     * @throws NullPointerException if the command is {@code null}.
     * @throws IllegalArgumentException if a command with one of the command's labels has already
     * been registered.
     */
    @Contract(mutates = "this")
    public void registerCommand(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        assert command.getLabels().size() > 0;

        for (final String label : command.getLabels()) {
            if (commands.containsKey(label)) {
                throw new IllegalArgumentException("Command with label \"" + label + "\" already exists");
            }
        }

        for (final String label : command.getLabels()) {
            commands.put(label, command);
        }
    }

    /**
     * Unregisters the command.
     *
     * @param command the command.
     * @throws IllegalArgumentException if
     */
    @Contract(mutates = "this")
    public void unregisterCommand(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        for (final String label : command.getLabels()) {
            if (!commands.remove(label, command)) {
                throw new IllegalArgumentException("Command with label \"" + label + "\" is not registered");
            }
        }
    }

    /**
     * Gets the command with the specified label.
     *
     * @param label the label.
     * @return an optional containing the command if it exists, otherwise {@code Optional.empty()}.
     * @throws NullPointerException if the label is {@code null}.
     */
    public @NotNull Optional<? extends @NotNull Command> getCommand(final @NotNull String label) {

        Objects.requireNonNull(label, "label cannot be null");

        return Optional.ofNullable(commands.get(label));
    }

    /**
     * Gets all the registered commands.
     *
     * @return an unmodifiable collection of all the registered commands.
     */
    public @NotNull @Unmodifiable Collection<? extends @NotNull Command> getRegisteredCommands() {

        return List.copyOf(commands.values());
    }
}
