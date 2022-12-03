package net.jailgens.enchanted;

import net.jailgens.mirror.Mirror;
import net.jailgens.mirror.TypeDefinition;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The shared implementation of {@link CommandFactory}
 *
 * @author Sparky983
 */
public final class SharedCommandFactory implements CommandFactory {

    private final @NotNull Mirror mirror;
    private final @NotNull CommandManager commandManager;

    @Contract(pure = true)
    SharedCommandFactory(final @NotNull Mirror mirror,
                         final @NotNull CommandManager commandManager) {

        Objects.requireNonNull(mirror, "mirror cannot be null");
        Objects.requireNonNull(commandManager, "commandManager cannot be null");

        this.mirror = mirror;
        this.commandManager = commandManager;
    }

    @Override
    public @NotNull CommandGroup createCommand(final @NotNull Object command) {

        return createCommand0(command);
    }

    @SuppressWarnings("unchecked")
    private <T extends @NotNull Object> @NotNull CommandGroup createCommand0(final @NotNull T command) {

        Objects.requireNonNull(command, "command cannot be null");

        final TypeDefinition<? extends T> type = mirror.reflect((Class<? extends T>) command.getClass());

        return new ClassCommand(command,
                type,
                new AnnotationCommandInfo(type.getAnnotations()),
                commandManager);
    }
}
