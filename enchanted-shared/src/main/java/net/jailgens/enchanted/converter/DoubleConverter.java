package net.jailgens.enchanted.converter;

import net.jailgens.enchanted.Converter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

final class DoubleConverter implements Converter<@NotNull Double> {

    @Override
    public Optional<@NotNull Double> convert(final @NotNull String value) {

        try {
            return Optional.of(Double.parseDouble(value));
        } catch (final NumberFormatException __) {
            return Optional.empty();
        }
    }
}
