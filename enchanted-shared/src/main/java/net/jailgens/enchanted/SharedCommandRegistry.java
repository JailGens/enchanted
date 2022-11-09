package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class SharedCommandRegistry implements CommandRegistry {

    private final @NotNull CommandFactory commandFactory;
    private final @NotNull Map<@NotNull String, @NotNull Command> commands = new HashMap<>();

    SharedCommandRegistry(@NotNull final CommandFactory commandFactory) {

        Objects.requireNonNull(commandFactory, "commandFactory cannot be null");

        this.commandFactory = commandFactory;
    }

    @Override
    public @NotNull Command registerCommand(final @NotNull Object command) {

        Objects.requireNonNull(command, "command cannot be null");

        final Command commandInstance = commandFactory.createCommand(command);

        assert commandInstance.getLabels().size() > 0;

        for (final String label : commandInstance.getLabels()) {
            if (commands.containsKey(label)) {
                throw new IllegalArgumentException("Multiple sub commands with label \"" + label + "\"");
            }
        }

        for (final String label : commandInstance.getLabels()) {
            commands.put(label, commandInstance);
        }

        return commandInstance;
    }

    @Override
    public void unregisterCommand(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        for (final String label : command.getLabels()) {
            if (!commands.remove(label, command)) {
                throw new IllegalArgumentException("Command with label \"" + label + "\" is not registered");
            }
        }
    }

    @Override
    public @NotNull Optional<? extends @NotNull Command> getCommand(final @NotNull String label) {

        Objects.requireNonNull(label, "label cannot be null");

        return Optional.ofNullable(commands.get(label));
    }

    @Override
    public @NotNull @Unmodifiable Collection<? extends @NotNull Command> getRegisteredCommands() {

        return List.copyOf(commands.values());
    }
}
