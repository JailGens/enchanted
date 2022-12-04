package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * A registry of {@link ArgumentResolver}s.
 *
 * @author Sparky983
 * @since 0.1.0
 */
public interface ArgumentResolverRegistry {

    /**
     * Registers the specified resolver.
     *
     * @param annotationType the annotation type.
     * @param resolver the resolver.
     * @param <T> the type of the annotation that specifies the resolver.
     * @throws NullPointerException if the annotation type or the resolver is {@code null}.
     * @since 0.1.0
     */
    @Contract(mutates = "this")
    <T extends @NotNull Annotation> void registerArgumentResolver(
            @NotNull Class<@NotNull T> annotationType,
            @NotNull ArgumentResolver<T> resolver);

    /**
     * Gets the resolver for the specified annotation type.
     *
     * @param annotationType the annotation type.
     * @param <T> the type of the annotation that specifies the resolver.
     * @return an optional containing the resolver, otherwise {@code Optional.empty()} if no
     * resolver for the specified annotation is registered.
     * @throws NullPointerException if the annotation type is {@code null}.
     * @since 0.1.0
     */
    <T extends @NotNull Annotation> @NotNull Optional<
            ? extends @NotNull ArgumentResolver<@NotNull T>>
    getArgumentResolver(@NotNull Class<@NotNull T> annotationType);
}
