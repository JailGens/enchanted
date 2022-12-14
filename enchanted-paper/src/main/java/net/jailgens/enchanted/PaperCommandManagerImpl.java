package net.jailgens.enchanted;

import net.jailgens.enchanted.converter.SharedConverterRegistry;
import net.jailgens.enchanted.converters.AudienceConverter;
import net.jailgens.enchanted.converters.MaterialConverter;
import net.jailgens.enchanted.converters.PlayerConverter;
import net.jailgens.enchanted.converters.WorldConverter;
import net.jailgens.enchanted.parser.SharedArgumentParserRegistry;
import net.jailgens.enchanted.resolver.SharedArgumentResolverRegistry;
import net.jailgens.mirror.Mirror;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link CommandManager} decorator for the paper platform.
 *
 * @author Sparky983
 */
final class PaperCommandManagerImpl implements PaperCommandManager {

    private final @NotNull PaperCommandFactory commandFactory;
    private final @NotNull PaperCommandRegistry commandRegistry;
    private final @NotNull ConverterRegistry converterRegistry;
    private final @NotNull ArgumentParserRegistry argumentParserRegistry;
    private final @NotNull ArgumentResolverRegistry argumentResolverRegistry;
    private final @NotNull TabCompleterRegistry tabCompleterRegistry;

    PaperCommandManagerImpl(final @NotNull Plugin plugin) {

        Objects.requireNonNull(plugin, "plugin cannot be null");

        final Mirror mirror = Mirror.builder().build();
        final PaperAdapter adapter = new PaperAdapterImpl(this);
        final Server server = plugin.getServer();

        this.commandFactory = new PaperCommandFactoryImpl(
                new SharedCommandFactory(mirror, this),
                adapter
        );

        this.commandRegistry = new PaperCommandRegistryImpl(
                new PaperCommandMap(
                        CommandMap.create(),
                        new CommandMapCommandAdapterImpl(),
                        plugin.getName().toLowerCase(),
                        server.getCommandMap()
                ),
                adapter
        );

        this.converterRegistry = new SharedConverterRegistry();
        converterRegistry.registerConverter(Audience.class, new AudienceConverter(server));
        converterRegistry.registerConverter(Material.class, new MaterialConverter());
        converterRegistry.registerConverter(Player.class, new PlayerConverter(server));
        converterRegistry.registerConverter(World.class, new WorldConverter(server));
        this.argumentParserRegistry = new SharedArgumentParserRegistry();
        this.argumentResolverRegistry = new SharedArgumentResolverRegistry();
        this.tabCompleterRegistry = new TabCompleterRegistryImpl(server);
    }

    @Override
    public @NotNull PaperCommandGroup createCommand(final @NotNull Object command) {

        Objects.requireNonNull(command, "command cannot be null");

        return commandFactory.createCommand(command);
    }

    @Override
    public @NotNull PaperCommandGroup registerCommand(final @NotNull Object command) {

        Objects.requireNonNull(command, "command cannot be null");

        final PaperCommandGroup paperCommandGroup = createCommand(command);
        registerCommand(paperCommandGroup);
        return paperCommandGroup;
    }

    @Override
    public @NotNull PaperCommand registerCommand(final @NotNull PaperCommand command) {

        Objects.requireNonNull(command, "command cannot be null");

        return commandRegistry.registerCommand(command);
    }

    @Override
    public @NotNull PaperCommand registerCommand(@NotNull final Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        return commandRegistry.registerCommand(command);
    }

    @Override
    public void unregisterCommand(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        commandRegistry.unregisterCommand(command);
    }

    @Override
    public @NotNull Optional<? extends @NotNull Command> getCommand(
            @Subst("command-name") final @NotNull String label) {

        Objects.requireNonNull(label, "label cannot be null");

        return commandRegistry.getCommand(label);
    }

    @Override
    public @NotNull @Unmodifiable Collection<? extends @NotNull Command> getRegisteredCommands() {

        return commandRegistry.getRegisteredCommands();
    }

    @Override
    public <T extends @NotNull Object> void registerConverter(
            final @NotNull Class<@NotNull T> type,
            final @NotNull Converter<@NotNull T> converter) {

        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(converter, "converter cannot be null");

        converterRegistry.registerConverter(type, converter);
    }

    @Override
    public <T extends @NotNull Object> Optional<? extends @NotNull Converter<@NotNull T>>
    getConverter(final @NotNull Class<@NotNull T> type) {

        Objects.requireNonNull(type, "type cannot be null");

        return converterRegistry.getConverter(type);
    }

    @Override
    public boolean hasConverter(final @NotNull Class<? extends @NotNull Object> type) {

        Objects.requireNonNull(type, "type cannot be null");

        return converterRegistry.hasConverter(type);
    }

    @Override
    public <T extends @NotNull Annotation> void registerArgumentParser(
            final @NotNull Class<@NotNull T> annotationType,
            final @NotNull ArgumentParser<@NotNull T> argumentParser) {

        Objects.requireNonNull(annotationType, "annotationType cannot be null");
        Objects.requireNonNull(argumentParser, "argumentParser cannot be null");

        argumentParserRegistry.registerArgumentParser(annotationType, argumentParser);
    }

    @Override
    public <T extends @NotNull Annotation> @NotNull Optional<
            ? extends @NotNull ArgumentParser<@NotNull T>>
    getArgumentParser(final @NotNull Class<@NotNull T> annotationType) {

        Objects.requireNonNull(annotationType, "annotationType cannot be null");

        return argumentParserRegistry.getArgumentParser(annotationType);
    }

    @Override
    public <T extends @NotNull Annotation> void registerArgumentResolver(
            final @NotNull Class<@NotNull T> annotationType,
            final @NotNull ArgumentResolver<T> resolver) {

        Objects.requireNonNull(annotationType, "annotationType cannot be null");
        Objects.requireNonNull(resolver, "resolver cannot be null");

        argumentResolverRegistry.registerArgumentResolver(annotationType, resolver);
    }

    @Override
    public @NotNull <T extends @NotNull Annotation>
    Optional<? extends @NotNull ArgumentResolver<@NotNull T>>
    getArgumentResolver(final @NotNull Class<@NotNull T> annotationType) {

        return argumentResolverRegistry.getArgumentResolver(annotationType);
    }

    @Override
    public <T extends @NotNull Object> void registerTabCompleter(
            final @NotNull Class<@NotNull T> type,
            final @NotNull TabCompleter<@NotNull T> completer) {

        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(completer, "completer cannot be null");

        tabCompleterRegistry.registerTabCompleter(type, completer);
    }

    @Override
    public @NotNull <T extends @NotNull Object> TabCompleter<@NotNull T>
    getTabCompleter(final @NotNull Class<T> type) {

        Objects.requireNonNull(type, "type cannot be null");

        return tabCompleterRegistry.getTabCompleter(type);
    }
}
