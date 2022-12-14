package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * A registry of converters.
 * <p>
 * By default, it must contain the following converters:
 * <ul>
 *     <li>{@link String}</li>
 *     <li>{@link Integer}, {@code int}</li>
 *     <li>{@link Double}, {@code double}</li>
 *     <li>{@link Float}, {@code float}</li>
 *     <li>{@link Boolean}, {@code boolean}</li>
 *     <li>{@link Long}, {@code long}</li>
 *     <li>{@link Short}, {@code short}</li>
 *     <li>{@link Byte}, {@code byte}</li>
 *     <li>{@link Character}, {@code char}</li>
 *     <li>{@link UUID}</li>
 *     <li>{@link Pattern}</li>
 *     <li>{@link Color}</li>
 * </ul>
 * The implementation of these converters is unspecified.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface ConverterRegistry {

    /**
     * Registers the specified converter.
     * <p>
     * If a converter for the specified type is already registered, it will be replaced.
     *
     * @param type the type.
     * @param converter the converter.
     * @param <T> the type of the converter converts to.
     * @throws NullPointerException if the converter is {@code null}.
     * @since 0.0.0
     */
    @Contract(mutates = "this")
    <T extends @NotNull Object> void registerConverter(
            @NotNull Class<@NotNull T> type,
            @NotNull Converter<? extends @NotNull T> converter);

    /**
     * Gets the converter for the specified type.
     *
     * @param type the type.
     * @param <T> the type of the converter converts to.
     * @return the converter.
     * @throws NullPointerException if the type is {@code null}.
     * @since 0.0.0
     */
    <T extends @NotNull Object> Optional<? extends @NotNull Converter<? extends @NotNull T>> getConverter(
            @NotNull Class<@NotNull T> type);

    /**
     * Checks if a converter for the specified type is registered.
     *
     * @param type the type.
     * @return {@code true} if a converter for the specified type is registered, {@code false} otherwise.
     * @throws NullPointerException if the type is {@code null}.
     * @since 0.0.0
     */
    boolean hasConverter(@NotNull Class<? extends @NotNull Object> type);
}
