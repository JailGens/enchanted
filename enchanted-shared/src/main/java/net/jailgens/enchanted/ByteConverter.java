package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

final class ByteConverter implements Converter<@NotNull Byte> {

    @Override
    public @NotNull Optional<@NotNull Byte> convert(final @NotNull String value) {

        try {
            return Optional.of(Byte.parseByte(value));
        } catch (final NumberFormatException __) {
            return Optional.empty();
        }
    }
}
