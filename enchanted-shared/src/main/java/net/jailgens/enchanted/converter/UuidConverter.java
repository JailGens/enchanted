package net.jailgens.enchanted.converter;

import net.jailgens.enchanted.ArgumentParseException;
import net.jailgens.enchanted.Converter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

final class UuidConverter implements Converter<@NotNull UUID> {

    @Override
    public @NotNull Optional<? extends @NotNull UUID> convert(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        try {
            return Optional.of(UUID.fromString(string));
        } catch (final IllegalArgumentException e) {
            throw new ArgumentParseException("UUID \"" + string + "\" is invalid", e);
        }
    }
}
