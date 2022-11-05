package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Function;

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
     * @param converter the converter.
     * @param type the type.
     * @param <T> the type of the converter converts to.
     * @throws NullPointerException if the converter is {@code null}.
     * @since 0.0.0
     */
    @Contract(mutates = "this")
    <T extends @NotNull Object> void registerConverter(
            @NotNull Function<? super @NotNull String, ? extends @NotNull T> converter,
            @NotNull Class<@NotNull T> type);

    /**
     * Converts the specified string to an instance of the specified type.
     * <p>
     * If a converter for the specified type is not registered, {@code Optional.empty()} is
     * returned.
     *
     * @param string the string.
     * @param type the type.
     * @param <T> the type of the converter converts to.
     * @return an optional containing the converted value, or {@code Optional.empty()} if no
     * converter was found.
     * @throws NullPointerException if the string or type is {@code null}.
     * @since 0.0.0
     */
    @NotNull <T extends @NotNull Object> Optional<? extends @NotNull T> convert(
            @NotNull String string, @NotNull Class<? extends @NotNull T> type);
}
