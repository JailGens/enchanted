package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

final class CharacterConverter implements Converter<@NotNull Character> {

    @Override
    public Optional<@NotNull Character> convert(final @NotNull String value) {

        if (value.length() == 1) {
            return Optional.of(value.charAt(0));
        } else {
            return Optional.empty();
        }
    }
}
