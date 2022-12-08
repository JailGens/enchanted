package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * A shared implementation of {@link CommandRegistry}.
 *
 * @author Sparky983
 */
public final class SharedCommandRegistry implements CommandRegistry {

    private final @NotNull CommandMap<@NotNull Command> commandMap;

    SharedCommandRegistry(final @NotNull CommandMap<@NotNull Command> commandMap) {

        Objects.requireNonNull(commandMap, "commandMap cannot be null");

        this.commandMap = commandMap;
    }

    @Override
    public @NotNull Command registerCommand(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        commandMap.registerCommand(command);
        return command;
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
