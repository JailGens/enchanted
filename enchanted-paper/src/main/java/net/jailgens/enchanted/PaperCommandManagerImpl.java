package net.jailgens.enchanted;

import net.jailgens.enchanted.converter.SharedConverterRegistry;
import net.jailgens.enchanted.parser.SharedArgumentParserRegistry;
import net.jailgens.mirror.Mirror;
import org.bukkit.plugin.Plugin;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

final class PaperCommandManagerImpl implements PaperCommandManager {

    private final @NotNull Plugin plugin;
    private final @NotNull CommandFactory commandFactory;
    private final @NotNull CommandRegistry commandRegistry;
    private final @NotNull ConverterRegistry converterRegistry = new SharedConverterRegistry();
    private final @NotNull ArgumentParserRegistry argumentParserRegistry = new SharedArgumentParserRegistry();

    @Contract(pure = true)
    PaperCommandManagerImpl(final @NotNull Plugin plugin) {

        Objects.requireNonNull(plugin, "plugin cannot be null");

        this.plugin = plugin;

        final Mirror mirror = Mirror.builder().build();

        this.commandFactory = new SharedCommandFactory(mirror, this);

        final CommandMap<Command> commandMap = new PaperCommandMap(
                CommandMap.create(),
                plugin.getName().toLowerCase(Locale.ROOT),
                plugin.getServer().getCommandMap());

        this.commandRegistry = new SharedCommandRegistry(commandFactory, commandMap);
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

    @Override
    public <T extends @NotNull Annotation> void registerArgumentParser(final @NotNull Class<@NotNull T> annotationType,
                                                                       final @NotNull ArgumentParser<@NotNull T> argumentParser) {

        argumentParserRegistry.registerArgumentParser(annotationType, argumentParser);
    }

    @Override
    public <T extends @NotNull Annotation> @NotNull Optional<
            ? extends @NotNull ArgumentParser<@NotNull T>>
    getArgumentParser(final @NotNull Class<@NotNull T> annotationType) {

        return argumentParserRegistry.getArgumentParser(annotationType);
    }
}
