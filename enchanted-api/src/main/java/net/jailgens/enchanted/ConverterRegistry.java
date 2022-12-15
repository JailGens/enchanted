package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * A registry of converters.
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
