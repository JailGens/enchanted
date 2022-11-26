package net.jailgens.enchanted;

import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Locale;

/**
 * Represents a command.
 * <p>
 * A command represented by this interface may be either a root-command, or a sub-command.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface Command extends Executable {

    /**
     * The pattern that all names must match.
     *
     * @since 0.0.0
     */
    @RegExp
    @NotNull String NAME_PATTERN = "[\\w-]+";

    /**
     * The pattern that all descriptions must match.
     *
     * @since 0.0.0
     */
    @RegExp
    @NotNull String DESCRIPTION_PATTERN = "[^\\n]*";

    /**
     * Gets the name of this command.
     *
     * @return the name of this command.
     * @since 0.0.0
     */
    @Contract(pure = true)
    @Pattern(NAME_PATTERN)
    @NotNull String getName();

    /**
     * Gets the aliases of this command.
     * <p>
     * Aliases are alternative names that a command sender can use instead of the actual name of
     * the command.
     * <p>
     * Here are some examples using common conventions:
     * <ol>
     *     <li>Shortened names (e.g. make {@literal ->} mk)</li>
     *     <li>Alternative names (e.g. make {@literal ->} create)</li>
     * </ol>
     *
     * @return the aliases of this command.
     * @since 0.0.0
     */
    @Contract(pure = true)
    @Unmodifiable @NotNull List</* @Pattern(NAME_PATTERN) */String> getAliases();

    /**
     * Gets the labels of this command.
     * <p>
     * Labels are all possible types of names a user can type to get this command, the name and the
     * aliases.
     *
     * @return the labels of this command.
     * @since 0.0.0
     */
    @Contract(pure = true)
    @Unmodifiable @NotNull List</* @Pattern(NAME_PATTERN) */String> getLabels();

    /**
     * Gets the description of this command for the specified locale.
     *
     * @param locale the locale.
     * @return the description for the specified locale.
     * @throws NullPointerException if the locale is {@code null}.
     * @since 0.0.0
     */
    @Contract(pure = true)
    @Pattern(DESCRIPTION_PATTERN)
    @NotNull String getDescription(@NotNull Locale locale);

    /**
     * Gets the description of this command for the default locale.
     *
     * @return the description for the default locale.
     * @since 0.0.0
     */
    @Contract(pure = true)
    @Pattern(DESCRIPTION_PATTERN)
    @NotNull String getDescription();
}
