package net.jailgens.enchanted.converter;

import net.jailgens.enchanted.Converter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

final class LongConverter implements Converter<@NotNull Long> {

    @Override
    public Optional<@NotNull Long> convert(final @NotNull String value) {

        try {
            return Optional.of(Long.parseLong(value));
        } catch (final NumberFormatException __) {
            return Optional.empty();
        }
    }
}
