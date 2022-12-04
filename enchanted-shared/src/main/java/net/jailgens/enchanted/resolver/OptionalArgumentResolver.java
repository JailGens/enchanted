package net.jailgens.enchanted.resolver;

import net.jailgens.enchanted.ArgumentParseException;
import net.jailgens.enchanted.ArgumentResolver;
import net.jailgens.enchanted.Arguments;
import net.jailgens.enchanted.CommandParameter;
import net.jailgens.enchanted.annotations.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class OptionalArgumentResolver implements ArgumentResolver<@NotNull Optional> {

    @Override
    public <T extends @Nullable Object> /*@NotNull*/ T resolve(
            final @NotNull CommandParameter<T> parameter,
            final @NotNull Optional annotation,
            final @NotNull Class<@NotNull T> type,
            final @NotNull Arguments arguments) {

        Objects.requireNonNull(parameter, "parameter cannot be null");
        Objects.requireNonNull(annotation, "annotation cannot be null");
        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(arguments, "arguments cannot be null");

        return arguments.pop()
                .map((argument) -> parameter.getConverter().convert(argument)
                        .orElseThrow(ArgumentParseException::new))
                .orElse(null);
    }
}
