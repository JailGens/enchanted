package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Provides an enchanted interface for {@link org.bukkit.command.CommandMap}.
 *
 * @author Sparky983
 */
final class PaperCommandMap implements CommandMap<@NotNull PaperCommand> {

    private final CommandMap<PaperCommand> commandMap;
    private final @NotNull CommandMapCommandAdapter commandAdapter;
    private final @NotNull String namespace;
    private final org.bukkit.command.@NotNull CommandMap bukkitCommandMap;
    private final @NotNull Map<@NotNull PaperCommand, org.bukkit.command.@NotNull Command>
            bukkitCommands = new HashMap<>();

    @Contract(pure = true)
    PaperCommandMap(final @NotNull CommandMap<@NotNull PaperCommand> commandMap,
                    final @NotNull CommandMapCommandAdapter commandAdapter,
                    final @NotNull String namespace,
                    final org.bukkit.command.@NotNull CommandMap bukkitCommandMap) {

        Objects.requireNonNull(commandMap, "commandMap cannot be null");
        Objects.requireNonNull(commandAdapter, "commandAdapter cannot be null");
        Objects.requireNonNull(namespace, "prefix cannot be null");
        Objects.requireNonNull(bukkitCommandMap, "bukkitCommandMap cannot be null");

        this.commandMap = commandMap;
        this.commandAdapter = commandAdapter;
        this.namespace = namespace;
        this.bukkitCommandMap = bukkitCommandMap;
    }

    @Override
    public void registerCommand(final @NotNull PaperCommand command) {

        Objects.requireNonNull(command, "command cannot be null");

        final org.bukkit.command.Command bukkitCommand = commandAdapter.adapt(command);

        bukkitCommandMap.register(namespace, bukkitCommand);
        bukkitCommands.put(command, bukkitCommand);
        commandMap.registerCommand(command);
    }

    @Override
    public void unregisterCommand(final @NotNull PaperCommand command) {

        Objects.requireNonNull(command, "command cannot be null");

        final org.bukkit.command.Command bukkitCommand = bukkitCommands.remove(command);

        if (bukkitCommand == null) {
            throw new IllegalStateException("Command is not registered");
        }

        bukkitCommand.unregister(bukkitCommandMap);

        commandMap.unregisterCommand(command);
    }

    @Override
    public @NotNull Optional<? extends @NotNull PaperCommand> getCommand(
            final @NotNull String label) {

        Objects.requireNonNull(label, "label cannot be null");

        return commandMap.getCommand(label);
    }

    @Override
    public @NotNull @Unmodifiable Collection<? extends @NotNull PaperCommand>
    getRegisteredCommands() {

        return commandMap.getRegisteredCommands();
    }
}
