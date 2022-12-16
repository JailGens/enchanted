package net.jailgens.enchanted.resolver;

import net.jailgens.enchanted.ArgumentParseException;
import net.jailgens.enchanted.Arguments;
import net.jailgens.enchanted.CommandParameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public final class DefaultCommandParameterResolver implements CommandParameterResolver {

    @Override
    public <T extends @Nullable Object> @NotNull T resolve(
            final @NotNull CommandParameter<T> parameter,
            final @NotNull Class<@NotNull T> type,
            final @NotNull Arguments arguments) {

        Objects.requireNonNull(parameter, "parameter cannot be null");
        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(arguments, "arguments cannot be null");

        final Optional<String> argument = arguments.pop();

        if (argument.isEmpty()) {
            throw new ArgumentParseException("argument is missing");
        }

        return parameter.getConverter().convert(argument.get())
                .orElseThrow(ArgumentParseException::new);
    }
}
