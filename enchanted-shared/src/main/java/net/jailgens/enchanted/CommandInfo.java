package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Holds command metadata.
 * <p>
 * It is important to note that this usually only holds user-defined data, so it may include
 * templates (in the future), and may also be invalid.
 *
 * @author Sparky983
 */
public interface CommandInfo {

    /**
     * Returns the raw, user-defined name.
     * <p>
     * In the future, since this is a user-defined name, it may include templates.
     *
     * @see Command#getName()
     */
    @Contract(pure = true)
    @NotNull Optional<@NotNull String> getName();

    /**
    * Returns the raw, user-defined aliases list.
    * <p>
    * In the future, since these aliases are user-defined, they may include templates.
     *
     * @see Command#getAliases()
     */
    @Contract(pure = true)
    @Unmodifiable @NotNull List<@NotNull String> getAliases();

    /**
     * Returns the raw, user-defined usage.
     * <p>
     * In the future, since this is a user-defined usage, it may include templates.
     *
     * @see Command#getUsage()
     */
    @Contract(pure = true)
    @NotNull Optional<String> getUsage();

    /**
     * Returns the raw, user-defined description.
     * <p>
     * In the future, since this is a user-defined usage, it may include templates.
     *
     * @see Command#getDescription()
     * @see Command#getDescription(Locale)
     */
    @Contract(pure = true)
    @NotNull Optional<@NotNull String> getDescription();
}
