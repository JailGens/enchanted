package net.jailgens.enchanted;

import net.jailgens.enchanted.converter.SharedConverterRegistry;
import net.jailgens.mirror.Mirror;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

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
    static @NotNull PaperCommandManager create(final Plugin plugin) {

        Objects.requireNonNull(plugin, "plugin cannot be null");

        final Mirror mirror = Mirror.builder().build();
        final ConverterRegistry converterRegistry = new SharedConverterRegistry();
        final UsageGenerator usageGenerator = new EmptyUsageGenerator();
        final CommandFactory commandFactory = new SharedCommandFactory(mirror,
                usageGenerator,
                new MethodCommandFactoryImpl(converterRegistry, usageGenerator));

        return new PaperCommandManagerImpl(
                plugin,
                plugin.getServer().getCommandMap(),
                commandFactory,
                new SharedCommandRegistry(commandFactory),
                converterRegistry);
    }
}
