package net.jailgens.enchanted.converter;

import net.jailgens.enchanted.ArgumentParseException;
import net.jailgens.enchanted.Converter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

final class PatternConverter implements Converter<@NotNull Pattern> {

    @Override
    public @NotNull Optional<? extends @NotNull Pattern> convert(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        try {
            return Optional.of(Pattern.compile(string));
        } catch (final PatternSyntaxException e) {
            throw new ArgumentParseException("Pattern \"" + string + "\" is invalid", e);
        }
    }
}
