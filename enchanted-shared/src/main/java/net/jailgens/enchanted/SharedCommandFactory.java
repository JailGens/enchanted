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
final class SharedCommandFactory implements CommandFactory {

    private final @NotNull Mirror mirror;
    private final @NotNull UsageGenerator usageGenerator;
    private final @NotNull MethodCommandFactory methodFactory;

    @Contract(pure = true)
    SharedCommandFactory(final @NotNull Mirror mirror,
                         final @NotNull UsageGenerator usageGenerator,
                         final @NotNull MethodCommandFactory methodFactory) {

        Objects.requireNonNull(mirror, "mirror cannot be null");
        Objects.requireNonNull(usageGenerator, "usageGenerator cannot be null");
        Objects.requireNonNull(methodFactory, "methodFactory cannot be null");

        this.mirror = mirror;
        this.usageGenerator = usageGenerator;
        this.methodFactory = methodFactory;
    }

    @Override
    public @NotNull Command createCommand(final @NotNull Object command) {

        return createCommand0(command);
    }

    @SuppressWarnings("unchecked")
    private <T extends @NotNull Object> @NotNull Command createCommand0(final @NotNull T command) {

        Objects.requireNonNull(command, "command cannot be null");

        final TypeDefinition<? extends T> type = mirror.reflect((Class<? extends T>) command.getClass());

        return new ClassCommand<>(command, type, usageGenerator, methodFactory);
    }
}
