package net.jailgens.enchanted.converter;

import net.jailgens.enchanted.Converter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

final class FloatConverter implements Converter<@NotNull Float> {

    @Override
    public Optional<? extends @NotNull Float> convert(final @NotNull String value) {

        try {
            return Optional.of(Float.parseFloat(value));
        } catch (final NumberFormatException __) {
            return Optional.empty();
        }
    }
}
