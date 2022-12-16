package net.jailgens.enchanted.converter;

import net.jailgens.enchanted.ArgumentParseException;
import net.jailgens.enchanted.Converter;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.Objects;
import java.util.Optional;

final class ColorConverter implements Converter<@NotNull Color> {

    @Override
    public @NotNull Optional<? extends @NotNull Color> convert(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        try {
            return Optional.of(Color.decode(string));
        } catch (final NumberFormatException e) {
            throw new ArgumentParseException("Color \"" + string + "\" is invalid", e);
        }
    }
}
