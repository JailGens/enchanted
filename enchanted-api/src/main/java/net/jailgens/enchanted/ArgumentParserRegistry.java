package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * A registry for {@link ArgumentParser}s.
 *
 * @author Sparky983
 * @since 0.1.0
 */
public interface ArgumentParserRegistry {

    /**
     * Registers the specified argument parser.
     * <p>
     * If one is already present, it will be overwritten.
     *
     * @param annotationType the annotation for the argument parser.
     * @param argumentParser the argument parser.
     * @param <T> the annotation type.
     * @since 0.1.0
     */
    @Contract(mutates = "this")
    <T extends @NotNull Annotation> void registerArgumentParser(
            @NotNull Class<@NotNull T> annotationType,
            @NotNull ArgumentParser<@NotNull T> argumentParser);

    /**
     * Gets the argument parser for the specified annotation type.
     *
     * @param annotationType the annotation type.
     * @param <T> the annotation type.
     * @return an optional containing the argument parser, otherwise {@code Optional.empty()} if
     * no argument parser for the specified annotation is registered..
     * @since 0.1.0
     */
    <T extends @NotNull Annotation> @NotNull Optional<? extends @NotNull ArgumentParser<@NotNull T>>
    getArgumentParser(@NotNull Class<@NotNull T> annotationType);
}
