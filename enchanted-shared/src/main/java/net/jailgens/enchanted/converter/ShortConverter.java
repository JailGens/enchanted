package net.jailgens.enchanted.converter;

import net.jailgens.enchanted.Converter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

final class ShortConverter implements Converter<@NotNull Short> {

    @Override
    public @NotNull Optional<? extends @NotNull Short> convert(final @NotNull String value) {

        try {
            return Optional.of(Short.parseShort(value));
        } catch (final NumberFormatException __) {
            return Optional.empty();
        }
    }
}
