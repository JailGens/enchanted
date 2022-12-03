package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Join;
import org.intellij.lang.annotations.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Represents a {@link Subcommand}'s parameter.
 *
 * @author Sparky983
 * @since 0.1.0
 */
public interface CommandParameter<T extends @NotNull Object> {

    /**
     * Gets this parameter's name.
     * <p>
     * The returned name can be assumed to not contain the space (SP) character.
     *
     * @return this parameter's name.
     * @since 0.1.0
     */
    @Contract(pure = true)
    @Identifier
    @NotNull String getName();

    /**
     * Returns whether this parameter is optional.
     *
     * @return whether this parameter is optional.
     * @since 0.1.0
     * @see net.jailgens.enchanted.annotations.Optional
     */
    @Contract(pure = true)
    boolean isOptional();

    /**
     * Returns delimiter that separates multiple arguments to this single parameter.
     * <p>
     * If this parameter does not join multiple arguments together, {@code Optional.empty()} is
     * returned.
     *
     * @return an optional containing the string that separates the arguments for this parameter.
     * @see Join
     * @since 0.1.0
     */
    @Contract(pure = true)
    @NotNull Optional<@NotNull String> getDelimiter();

    /**
     * Returns this parameter's converter.
     *
     * @return this parameter's converter.
     * @see net.jailgens.enchanted.annotations.Optional
     * @see Converter
     * @since 0.1.0
     */
    @Contract(pure = true)
    @NotNull Converter<@NotNull T> getConverter();

    /**
     * Parses the specified argument into a {@link String} that is usable by an {@link Converter}.
     * <p>
     * This directly calls the parameter's {@link ArgumentParser} with the annotation on the
     * parameter.
     *
     * @param arguments the arguments.
     * @return an optional containing the parsed argument or {@code Optional.empty()} if no more
     * arguments are left.
     * @see ArgumentParser
     * @since 0.1.0
     */
    @NotNull Optional<@NotNull String> parse(@NotNull Arguments arguments);
}
