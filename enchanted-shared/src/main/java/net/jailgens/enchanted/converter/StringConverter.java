package net.jailgens.enchanted.converter;

import net.jailgens.enchanted.Converter;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

final class StringConverter implements Converter<@NotNull String> {

    @Override
    public @NotNull Optional<@NotNull String> convert(final @NotNull String value) {

        return Optional.of(value);
    }
}
