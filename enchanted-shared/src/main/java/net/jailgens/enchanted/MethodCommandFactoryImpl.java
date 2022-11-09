package net.jailgens.enchanted;

import net.jailgens.mirror.Method;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class MethodCommandFactoryImpl implements MethodCommandFactory {

    private final @NotNull ConverterRegistry converterRegistry;
    private final @NotNull UsageGenerator usageGenerator;

    MethodCommandFactoryImpl(final @NotNull ConverterRegistry converterRegistry,
                             final @NotNull UsageGenerator usageGenerator) {

        Objects.requireNonNull(converterRegistry, "converterRegistry cannot be null");
        Objects.requireNonNull(usageGenerator, "usageGenerator cannot be null");

        this.converterRegistry = converterRegistry;
        this.usageGenerator = usageGenerator;
    }

    @Override
    public <T extends @NotNull Object> @NotNull Command createCommand(
            final @NotNull T command,
            final @NotNull Method<? extends @NotNull T, ? extends @NotNull Object> method) {

        Objects.requireNonNull(method, "method cannot be null");
        Objects.requireNonNull(command, "command cannot be null");

        return new MethodCommand<>(command, method, converterRegistry, usageGenerator);
    }
}
