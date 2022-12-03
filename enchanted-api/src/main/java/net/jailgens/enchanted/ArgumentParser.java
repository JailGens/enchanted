package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * Parsers an argument into a {@link String} that is usable by an {@link Converter}.
 *
 * @param <T>
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
     * @throws NullPointerException if the annotation or the arguments is {@code null}.
     * @since 0.1.0
     */
    @NotNull Optional<@NotNull String> parse(@NotNull T annotation, @NotNull Arguments arguments);
}
