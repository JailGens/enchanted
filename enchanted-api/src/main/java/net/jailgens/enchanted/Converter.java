package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@FunctionalInterface
public interface Converter<T extends @NotNull Object> {

    /**
     * Converts the specified string to an object of the specified type.
     *
     * @param string the string.
     * @return an optional containing the converted object or {@code Optional.empty()} if the string
     * was unable to be converted.
     * @throws ArgumentParseException if the argument could not be parsed due to the arguments being
     * unparsable by this converter.
     * @throws NullPointerException the converter may optionally throw this exception if the string
     * is {@code null}.
     * @since 0.0.0
     */
    @NotNull Optional<? extends @NotNull T> convert(@NotNull String string);
}
