package net.jailgens.enchanted;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Locale;

import static net.jailgens.enchanted.Command.DESCRIPTION_PATTERN;
import static net.jailgens.enchanted.Command.NAME_PATTERN;

/**
 * Holds command metadata.
 *
 * @author Sparky983
 */
public interface CommandInfo {

    /**
     * Gets the name.
     *
     * @return the name.
     * @see Command#getName()
     */
    @Contract(pure = true)
    @Pattern(NAME_PATTERN)
    @NotNull String getName();

    /**
     * Gets the aliases.
     *
     * @return the aliases.
     * @see Command#getAliases()
     */
    @Contract(pure = true)
    @Unmodifiable @NotNull List</* @Pattern(NAME_PATTERN) */@NotNull String> getAliases();

    /**
     * Gets the labels.
     *
     * @return the labels.
     * @see Command#getLabels()
     */
    @Contract(pure = true)
    @Unmodifiable @NotNull List</* @Pattern(NAME_PATTERN) */@NotNull String> getLabels();

    /**
     * Gets the raw, user-defined description.
     * <p>
     * In the future, since this is a user-defined string description, it may include templates.
     *
     * @return the raw, user-defined description.
     * @see Command#getDescription()
     * @see Command#getDescription(Locale)
     */
    @Contract(pure = true)
    @Pattern(DESCRIPTION_PATTERN)
    @NotNull String getDescription();
}
