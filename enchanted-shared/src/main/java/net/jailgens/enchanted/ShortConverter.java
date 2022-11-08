package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

final class ShortConverter implements Converter<@NotNull Short> {

    @Override
    public @NotNull Optional<@NotNull Short> convert(final @NotNull String value) {

        try {
            return Optional.of(Short.parseShort(value));
        } catch (final NumberFormatException __) {
            return Optional.empty();
        }
    }
}
