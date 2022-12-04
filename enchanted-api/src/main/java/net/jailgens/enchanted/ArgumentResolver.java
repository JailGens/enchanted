package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;

/**
 * Turns a command argument into an object of type {@link A}.
 *
 * @author Sparky983
 * @param <A> the type of the annotation that specifies this resolver.
 * @since 0.1.0
 */
@FunctionalInterface
public interface ArgumentResolver<A extends @NotNull Annotation> {

    /**
     * Resolves the specified arguments into an object of type {@link T}.
     *
     * @param parameter the command parameter.
     * @param annotation the annotation.
     * @param type the type of the object.
     * @param arguments the arguments after they have been parsed with an {@link ArgumentParser}.
     * @return the resolved object.
     * @param <T> the type of the object.
     * @throws ArgumentParseException if the arguments could not be resolved.
     * @throws NullPointerException if the command parameter, annotation, type or arguments is
     * {@code null} (optional).
     * @since 0.1.0
     */
    <T extends @Nullable Object> /*@NotNull*/ T resolve(@NotNull CommandParameter<T> parameter,
                                                        @NotNull A annotation,
                                                        @NotNull Class<@NotNull T> type,
                                                        @NotNull Arguments arguments);

    // @NotNull is commented out because by Kotlin and JSpecify rules, the return value may contain
    // null if the <T> is @Nullable. But the IDE says this is equivalent to the JSpecify MINUS_NULL
    // operator

    // Optional<T> isn't applicable here because the result is directly handed to the method
}