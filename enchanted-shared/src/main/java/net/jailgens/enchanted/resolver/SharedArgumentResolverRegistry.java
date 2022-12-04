package net.jailgens.enchanted.resolver;

import net.jailgens.enchanted.ArgumentResolver;
import net.jailgens.enchanted.ArgumentResolverRegistry;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * The shared implementation of {@link ArgumentResolverRegistry}.
 *
 * @author Sparky983
 */
public final class SharedArgumentResolverRegistry implements ArgumentResolverRegistry {

    private final @NotNull Map<@NotNull Class<? extends @NotNull Object>, ArgumentResolver<? extends @NotNull Object>> argumentResolvers = new HashMap<>();

    @Override
    public <T extends @NotNull Annotation> void registerArgumentResolver(
            final @NotNull Class<@NotNull T> annotationType,
            final @NotNull ArgumentResolver<T> resolver) {

        Objects.requireNonNull(annotationType, "annotationType cannot be null");
        Objects.requireNonNull(resolver, "resolver cannot be null");

        argumentResolvers.put(annotationType, resolver);
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull <T extends @NotNull Annotation>
    Optional<? extends @NotNull ArgumentResolver<@NotNull T>> getArgumentResolver(
            final @NotNull Class<@NotNull T> annotationType) {

        Objects.requireNonNull(annotationType, "annotationType cannot be null");

        return Optional.ofNullable((ArgumentResolver<T>) argumentResolvers.get(annotationType));
    }
}
