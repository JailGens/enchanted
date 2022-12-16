package net.jailgens.enchanted.converter;

import net.jailgens.enchanted.Converter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A generic {@link Enum} {@link Converter}.
 *
 * @author Sparky983
 * @param <T> the type of the enum.
 */
public final class EnumConverter<@NotNull T extends @NotNull Enum<@NotNull T>>
        implements Converter<@NotNull T> {

    private final @NotNull Map<@NotNull String, @NotNull T> caseInsensitiveEnumValues;

    @Contract(pure = true)
    public EnumConverter(final @NotNull Class<@NotNull T> enumType) {

        Objects.requireNonNull(enumType, "enumType cannot be null");

        this.caseInsensitiveEnumValues = Arrays.stream(enumType.getEnumConstants())
                .collect(Collectors.toUnmodifiableMap(
                        enumValue -> enumValue.name().toLowerCase(Locale.ROOT),
                        enumValue -> enumValue
                ));
    }

    @Override
    public @NotNull Optional<? extends @NotNull T> convert(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        return Optional.ofNullable(caseInsensitiveEnumValues.get(string.toLowerCase(Locale.ROOT)));
    }
}
