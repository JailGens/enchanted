package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A map of command labels to commands.
 *
 * @author Sparky983
 */
final class CommandMapImpl implements CommandMap {

    private final @NotNull Map<@NotNull String, @NotNull Command> commands = new HashMap<>();

    @Override
    public void registerCommand(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        assert command.getLabels().size() > 0;

        for (final String label : command.getLabels()) {
            if (commands.containsKey(label.toLowerCase(Locale.ROOT))) {
                throw new IllegalArgumentException("Command with label \"" + label.toLowerCase(Locale.ROOT) + "\" already exists");
            }
        }

        for (final String label : command.getLabels()) {
            commands.put(label.toLowerCase(Locale.ROOT), command);
        }
    }

    @Override
    public void unregisterCommand(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        for (final String label : command.getLabels()) {
            if (!commands.remove(label.toLowerCase(Locale.ROOT), command)) {
                throw new IllegalArgumentException("Command with label \"" + label + "\" is not registered");
            }
        }
    }

    @Override
    public @NotNull Optional<? extends @NotNull Command> getCommand(final @NotNull String label) {

        Objects.requireNonNull(label, "label cannot be null");

        return Optional.ofNullable(commands.get(label.toLowerCase(Locale.ROOT)));
    }

    @Override
    public @NotNull @Unmodifiable Collection<? extends @NotNull Command> getRegisteredCommands() {

        return List.copyOf(commands.values());
    }
}
