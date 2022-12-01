package net.jailgens.enchanted;

import org.bukkit.plugin.Plugin;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

final class PaperCommandManagerImpl implements PaperCommandManager {

    private final @NotNull Plugin plugin;
    private final @NotNull CommandFactory commandFactory;
    private final @NotNull CommandRegistry commandRegistry;
    private final @NotNull ConverterRegistry converterRegistry;

    @Contract(pure = true)
    PaperCommandManagerImpl(final @NotNull Plugin plugin,
                            final @NotNull CommandFactory commandFactory,
                            final @NotNull CommandRegistry commandRegistry,
                            final @NotNull ConverterRegistry converterRegistry) {

        Objects.requireNonNull(plugin, "plugin cannot be null");
        Objects.requireNonNull(commandFactory, "commandFactory cannot be null");
        Objects.requireNonNull(commandRegistry, "commandRegistry cannot be null");
        Objects.requireNonNull(converterRegistry, "converterRegistry cannot be null");

        this.plugin = plugin;
        this.commandFactory = commandFactory;
        this.commandRegistry = commandRegistry;
        this.converterRegistry = converterRegistry;
    }

    @Override
    public @NotNull CommandGroup createCommand(final @NotNull Object command) {

        return commandFactory.createCommand(command);
    }

    @Override
    public @NotNull CommandGroup registerCommand(final @NotNull Object command) {

        return commandRegistry.registerCommand(command);
    }

    @Override
    public void unregisterCommand(final @NotNull Command command) {

        commandRegistry.unregisterCommand(command);
    }

    @Override
    public @NotNull Optional<? extends @NotNull Command> getCommand(
            @Subst("command-name") final @NotNull String label) {

        return commandRegistry.getCommand(label);
    }

    @Override
    public @NotNull @Unmodifiable Collection<? extends @NotNull Command> getRegisteredCommands() {

        return commandRegistry.getRegisteredCommands();
    }

    @Override
    public <T> void registerConverter(final @NotNull Class<@NotNull T> type,
                                      final @NotNull Converter<@NotNull T> converter) {

        converterRegistry.registerConverter(type, converter);
    }

    @Override
    public <T> Optional<? extends @NotNull Converter<@NotNull T>> getConverter(final @NotNull Class<@NotNull T> type) {

        return converterRegistry.getConverter(type);
    }

    @Override
    public boolean hasConverter(final @NotNull Class<?> type) {

        return converterRegistry.hasConverter(type);
    }
}
