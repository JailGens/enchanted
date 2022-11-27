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
 * A command map decorator that also registers commands to the server.
 *
 * @author Sparky983
 */
final class PaperCommandMap implements CommandMap {

    private final @NotNull CommandMap commandMap;
    private final @NotNull String namespace;
    private final org.bukkit.command.@NotNull CommandMap bukkitCommandMap;
    private final @NotNull Map<@NotNull Command, org.bukkit.command.@NotNull Command>
            bukkitCommands = new HashMap<>();

    @Contract(pure = true)
    PaperCommandMap(final @NotNull CommandMap commandMap,
                    final @NotNull String namespace,
                    final org.bukkit.command.@NotNull CommandMap bukkitCommandMap) {

        Objects.requireNonNull(commandMap, "commandMap cannot be null");
        Objects.requireNonNull(namespace, "prefix cannot be null");
        Objects.requireNonNull(bukkitCommandMap, "bukkitCommandMap cannot be null");

        this.commandMap = commandMap;
        this.namespace = namespace;
        this.bukkitCommandMap = bukkitCommandMap;
    }

    @Override
    public void registerCommand(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        bukkitCommandMap.register(namespace, new PaperCommand(command));
        commandMap.registerCommand(command);
    }

    @Override
    public void unregisterCommand(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        final org.bukkit.command.Command bukkitCommand = bukkitCommands.get(command);
        bukkitCommand.unregister(bukkitCommandMap);
        bukkitCommands.remove(command, bukkitCommand);
        commandMap.unregisterCommand(command);
    }

    @Override
    public @NotNull Optional<? extends @NotNull Command> getCommand(final @NotNull String label) {

        return commandMap.getCommand(label);
    }

    @Override
    public @NotNull @Unmodifiable Collection<? extends @NotNull Command> getRegisteredCommands() {

        return commandMap.getRegisteredCommands();
    }
}
