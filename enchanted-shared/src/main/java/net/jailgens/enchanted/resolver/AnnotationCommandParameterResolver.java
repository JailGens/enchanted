package net.jailgens.enchanted.resolver;

import net.jailgens.enchanted.ArgumentResolver;
import net.jailgens.enchanted.Arguments;
import net.jailgens.enchanted.CommandParameter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.Objects;

public final class AnnotationCommandParameterResolver<A extends @NotNull Annotation> implements CommandParameterResolver {

    private final @NotNull ArgumentResolver<@NotNull A> resolver;
    private final @NotNull A annotation;

    @Contract(pure = true)
    public AnnotationCommandParameterResolver(
            final @NotNull A annotation,
            final @NotNull ArgumentResolver<@NotNull A> resolver) {

        Objects.requireNonNull(annotation, "annotation cannot be null");
        Objects.requireNonNull(resolver, "resolver cannot be null");

        this.annotation = annotation;
        this.resolver = resolver;
    }

    @Override
    public <T extends @Nullable Object> /*@NotNull*/ T resolve(
            final @NotNull CommandParameter<T> parameter,
            final @NotNull Class<@NotNull T> type,
            final @NotNull Arguments arguments) {

        Objects.requireNonNull(parameter, "parameter cannot be null");
        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(arguments, "arguments cannot be null");

        return resolver.resolve(parameter, annotation, type, arguments);
    }
}
