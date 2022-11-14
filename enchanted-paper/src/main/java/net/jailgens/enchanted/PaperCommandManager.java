package net.jailgens.enchanted;

import net.jailgens.enchanted.converter.SharedConverterRegistry;
import net.jailgens.mirror.Mirror;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.awt.print.Paper;
import java.util.Locale;
import java.util.Objects;

/**
 * The paper implementation of {@link CommandManager}.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface PaperCommandManager extends CommandManager {

    /**
     * Creates a new command manager.
     *
     * @param plugin the plugin.
     * @return the command manager.
     * @since 0.0.0
     */
    static @NotNull PaperCommandManager create(final @NotNull Plugin plugin) {

        Objects.requireNonNull(plugin, "plugin cannot be null");

        final Mirror mirror = Mirror.builder().build();
        final ConverterRegistry converterRegistry = new SharedConverterRegistry();
        final UsageGenerator usageGenerator = new EmptyUsageGenerator();
        final CommandFactory commandFactory = new SharedCommandFactory(mirror,
                usageGenerator,
                new MethodCommandFactoryImpl(converterRegistry, usageGenerator));

        return new PaperCommandManagerImpl(
                plugin,
                new PaperCommandMap(
                        CommandMap.create(),
                        plugin.getName().toLowerCase(Locale.ROOT),
                        plugin.getServer().getCommandMap()),
                commandFactory,
                new SharedCommandRegistry(commandFactory, CommandMap.create()),
                converterRegistry);
    }
}
