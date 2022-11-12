package net.jailgens.enchanted.converter;

import net.jailgens.enchanted.Converter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

final class IntegerConverter implements Converter<@NotNull Integer> {

    @Override
    public @NotNull Optional<@NotNull Integer> convert(final @NotNull String value) {

        try {
            return Optional.of(Integer.parseInt(value));
        } catch (final NumberFormatException __) {
            return Optional.empty();
        }
    }
}
