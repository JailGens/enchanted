package net.jailgens.enchanted.parser;

import net.jailgens.enchanted.ArgumentParser;
import net.jailgens.enchanted.Arguments;
import net.jailgens.enchanted.annotations.Join;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * The argument parser for {@link Join}.
 *
 * @author Sparky983
 */
public final class JoinArgumentParser implements ArgumentParser<@NotNull Join> {

    @Override
    public @NotNull Optional<@NotNull String> parse(final @NotNull Join annotation,
                                                    final @NotNull Arguments arguments) {

        Objects.requireNonNull(annotation, "annotation cannot be null");
        Objects.requireNonNull(arguments, "arguments cannot be null");

        if (arguments.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(String.join(annotation.value(), arguments));
    }
}
