package net.jailgens.enchanted;

import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

final class PaperCommandManagerImpl implements PaperCommandManager {

    private final CommandFactory commandFactory;
    private final CommandRegistry commandRegistry;
    private final ConverterRegistry converterRegistry;

    @Contract(pure = true)
    PaperCommandManagerImpl(final @NotNull CommandFactory commandFactory,
                            final @NotNull CommandRegistry commandRegistry,
                            final @NotNull ConverterRegistry converterRegistry) {

        Objects.requireNonNull(commandFactory, "commandFactory cannot be null");
        Objects.requireNonNull(commandRegistry, "commandRegistry cannot be null");
        Objects.requireNonNull(converterRegistry, "converterRegistry cannot be null");

        this.commandFactory = commandFactory;
        this.commandRegistry = commandRegistry;
        this.converterRegistry = converterRegistry;
    }

    @Override
    public @NotNull Command createCommand(final @NotNull Object command) {

        return commandFactory.createCommand(command);
    }

    @Override
    public @NotNull Command registerCommand(final @NotNull Object command) {

        return commandFactory.createCommand(command);
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
    public <T> Optional<@NotNull Converter<@NotNull T>> getConverter(final @NotNull Class<@NotNull T> type) {

        return converterRegistry.getConverter(type);
    }

    @Override
    public boolean hasConverter(final @NotNull Class<?> type) {

        return converterRegistry.hasConverter(type);
    }
}
