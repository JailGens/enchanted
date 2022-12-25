package net.jailgens.enchanted;

import net.jailgens.enchanted.converter.SharedConverterRegistry;
import net.jailgens.enchanted.parser.SharedArgumentParserRegistry;
import net.jailgens.enchanted.resolver.SharedArgumentResolverRegistry;
import net.jailgens.mirror.Mirror;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Optional;

public final class SharedCommandManager implements CommandManager {

    private final @NotNull CommandFactory commandFactory;
    private final @NotNull CommandRegistry commandRegistry;
    private final @NotNull ConverterRegistry converterRegistry = new SharedConverterRegistry();
    private final @NotNull ArgumentParserRegistry argumentParserRegistry =
            new SharedArgumentParserRegistry();
    private final @NotNull ArgumentResolverRegistry argumentResolverRegistry =
            new SharedArgumentResolverRegistry();

    @Contract(pure = true)
    private SharedCommandManager() {

        final Mirror mirror = Mirror.builder().build();
        final CommandMap<Command> commandMap = CommandMap.create();

        this.commandFactory = new SharedCommandFactory(mirror, this);
        this.commandRegistry = new SharedCommandRegistry(commandMap);
    }

    @Contract(value = "-> new", pure = true)
    public static @NotNull CommandManager create() {

        return new SharedCommandManager();
    }

    @Override
    public @NotNull CommandGroup createCommand(final @NotNull Object command) {

        return commandFactory.createCommand(command);
    }

    @Override
    public @NotNull CommandGroup registerCommand(final @NotNull Object command) {

        final CommandGroup commandGroup = createCommand(command);

        registerCommand(commandGroup);

        return commandGroup;
    }

    @Override
    public @NotNull Command registerCommand(final @NotNull Command command) {

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
                                      final @NotNull Converter<? extends @NotNull T> converter) {

        converterRegistry.registerConverter(type, converter);
    }

    @Override
    public <T> Optional<? extends @NotNull Converter<? extends @NotNull T>>
    getConverter(final @NotNull Class<@NotNull T> type) {

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

    @Override
    public <T extends @NotNull Annotation> void registerArgumentResolver(
            final @NotNull Class<@NotNull T> annotationType,
            final @NotNull ArgumentResolver<T> resolver) {

        argumentResolverRegistry.registerArgumentResolver(annotationType, resolver);
    }

    @Override
    public @NotNull <T extends @NotNull Annotation>
    Optional<? extends @NotNull ArgumentResolver<@NotNull T>>
    getArgumentResolver(final @NotNull Class<@NotNull T> annotationType) {

        return argumentResolverRegistry.getArgumentResolver(annotationType);
    }
}
