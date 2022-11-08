package net.jailgens.enchanted;

import net.jailgens.mirror.Mirror;
import org.jetbrains.annotations.NotNull;

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
     * @return the command manager.
     * @since 0.0.0
     */
    static @NotNull PaperCommandManager create() {

        final Mirror mirror = Mirror.builder().build();
        final ConverterRegistry converterRegistry = new SharedConverterRegistry();
        final UsageGenerator usageGenerator = new EmptyUsageGenerator();
        final CommandFactory commandFactory = new SharedCommandFactory(mirror,
                usageGenerator,
                new MethodCommandFactoryImpl(converterRegistry, usageGenerator));

        return new PaperCommandManagerImpl(
                commandFactory,
                new SharedCommandRegistry(commandFactory),
                converterRegistry);
    }
}
