package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * Parsers an argument into a {@link String} that is usable by an {@link Converter}.
 *
 * @param <T> the type of the annotation that specifies this parser.
 * @since 0.1.0
 */
public interface ArgumentParser<T extends @NotNull Annotation> {

    /**
     * Parses the specified argument into a {@link String} that is usable by an {@link Converter}.
     *
     * @param annotation the annotation.
     * @param arguments the arguments.
     * @return an optional containing the parsed argument or {@code Optional.empty()} if no more
     * arguments are left.
     * @throws ArgumentParseException if the argument could not be parsed due to the arguments being
     * unparsable by this argument parser.
     * @throws NullPointerException if the annotation or the arguments is {@code null} (optional).
     * @since 0.1.0
     */
    @Contract(mutates = "param1")
    @NotNull Optional<@NotNull String> parse(@NotNull T annotation, @NotNull Arguments arguments);
}
