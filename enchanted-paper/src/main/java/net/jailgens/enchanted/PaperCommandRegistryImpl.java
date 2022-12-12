package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

final class PaperCommandRegistryImpl implements PaperCommandRegistry {

    private final @NotNull PaperAdapter adapter;
    private final @NotNull CommandMap<@NotNull PaperCommand> commandMap;

    @Contract(pure = true)
    PaperCommandRegistryImpl(final @NotNull CommandMap<@NotNull PaperCommand> commandMap,
                             final @NotNull PaperAdapter adapter) {

        Objects.requireNonNull(commandMap, "commandMap cannot be null");
        Objects.requireNonNull(adapter, "adapter cannot be null");

        this.commandMap = commandMap;
        this.adapter = adapter;
    }

    @Override
    public @NotNull PaperCommand registerCommand(final @NotNull PaperCommand command) {

        Objects.requireNonNull(command, "command cannot be null");

        commandMap.registerCommand(command);
        return command;
    }

    @Override
    public @NotNull PaperCommand registerCommand(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        return registerCommand(adapter.adapt(command));
    }

    @Override
    public void unregisterCommand(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        if (command instanceof PaperCommand) {
            // TODO(Sparky983): This feels kinda hacky but I don't know what else to do
            commandMap.unregisterCommand((PaperCommand) command);
        }

        throw new IllegalArgumentException("Command is not registered");
    }

    @Override
    public @NotNull Optional<? extends @NotNull PaperCommand> getCommand(final @NotNull String label) {

        Objects.requireNonNull(label, "label cannot be null");

        return commandMap.getCommand(label);
    }

    @Override
    public @NotNull @Unmodifiable Collection<? extends @NotNull PaperCommand> getRegisteredCommands() {

        return commandMap.getRegisteredCommands();
    }
}
