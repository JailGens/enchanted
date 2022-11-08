package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

final class BooleanConverter implements Converter<@NotNull Boolean> {

    @Override
    public Optional<@NotNull Boolean> convert(final @NotNull String value) {

        if (value.equalsIgnoreCase("true")) {
            return Optional.of(true);
        } else if (value.equalsIgnoreCase("false")) {
            return Optional.of(false);
        } else {
            return Optional.empty();
        }
    }
}
