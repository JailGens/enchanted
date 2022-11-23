package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public final class SharedCommandRegistry implements CommandRegistry {

    private final @NotNull CommandFactory commandFactory;
    private final @NotNull CommandMap commandMap;

    SharedCommandRegistry(final @NotNull CommandFactory commandFactory,
                          final @NotNull CommandMap commandMap) {

        Objects.requireNonNull(commandFactory, "commandFactory cannot be null");
        Objects.requireNonNull(commandMap, "commandMap cannot be null");

        this.commandFactory = commandFactory;
        this.commandMap = commandMap;
    }

    @Override
    public @NotNull Command registerCommand(final @NotNull Object command) {

        Objects.requireNonNull(command, "command cannot be null");

        final Command commandInstance = commandFactory.createCommand(command);

        commandMap.registerCommand(commandInstance);
        return commandInstance;
    }

    @Override
    public void unregisterCommand(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        commandMap.unregisterCommand(command);
    }

    @Override
    public @NotNull Optional<? extends @NotNull Command> getCommand(final @NotNull String label) {

        Objects.requireNonNull(label, "label cannot be null");

        return commandMap.getCommand(label);
    }

    @Override
    public @NotNull @Unmodifiable Collection<? extends @NotNull Command> getRegisteredCommands() {

        return commandMap.getRegisteredCommands();
    }
}
