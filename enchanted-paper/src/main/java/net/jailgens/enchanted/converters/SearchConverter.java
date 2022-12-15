package net.jailgens.enchanted.converters;

import net.jailgens.enchanted.ArgumentParseException;
import net.jailgens.enchanted.Converter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * A converter wrapper that wraps another converter that usually searches for something and returns
 * an empty optional if the search comes up empty. This converter will throw an
 * {@link ArgumentParseException} with a more descriptive message instead of a generic message
 * used when {@link Optional#empty()} is returned.
 *
 * @author Sparky983
 */
public final class SearchConverter<T extends @NotNull Object> implements Converter<@NotNull T> {

    /**
     * The name of the type of the completer/converter.
     * <p>
     * This is used in the error message when the string cannot be converted.
     * <p>
     * Usually the name of {@link T}.
     * <p>
     * Examples:
     * <ul>
     *     <li>Player</li>
     *     <li>Material</li>
     *     <li>etc.</li>
     * </ul>
     */
    private final @NotNull String typeName;
    private final Converter<T> converter;

    @Contract(pure = true)
    public SearchConverter(final @NotNull String typeName,
                           final @NotNull Converter<T> converter) {

        Objects.requireNonNull(typeName, "typeName cannot be null");
        Objects.requireNonNull(converter, "converter cannot be null");

        this.typeName = typeName;
        this.converter = converter;
    }

    @Override
    public @NotNull Optional<? extends @NotNull T> convert(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        final Optional<? extends T> optional = converter.convert(string);

        if (optional.isEmpty()) {
            throw new ArgumentParseException(typeName + " \"" + string + "\" not found");
        }

        return optional;
    }
}
