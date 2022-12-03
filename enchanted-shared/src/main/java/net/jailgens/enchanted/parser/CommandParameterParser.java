package net.jailgens.enchanted.parser;

import net.jailgens.enchanted.ArgumentParseException;
import net.jailgens.enchanted.ArgumentParser;
import net.jailgens.enchanted.Arguments;
import net.jailgens.enchanted.Converter;
import net.jailgens.enchanted.resolver.CommandParameterResolver;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * An {@link ArgumentParser} that isn't dependent on an annotation.
 * <p>
 * This has two implementations: one for the default argument parser (no annotation) and the other
 * by adapting {@link ArgumentParser}s (with annotation) to this.
 *
 * @author Sparky983
 * @see CommandParameterResolver
 */
@FunctionalInterface
public interface CommandParameterParser {

    /**
     * The default argument parser.
     * <p>
     * This is used when no annotation is present.
     * <p>
     * Simply returns the popped argument.
     */
    @NotNull CommandParameterParser DEFAULT = Arguments::pop;

    /**
     * Parses the specified argument into a {@link String} that is usable by an {@link Converter}.
     *
     * @param arguments the arguments.
     * @return an optional containing the parsed argument or {@code Optional.empty()} if no more
     * arguments are left.
     * @throws ArgumentParseException if the argument could not be parsed due to the arguments being
     * unparsable.
     * @throws NullPointerException if the arguments is {@code null} (optional).
     */
    @NotNull Optional<@NotNull String> parse(@NotNull Arguments arguments) throws ArgumentParseException;
}
