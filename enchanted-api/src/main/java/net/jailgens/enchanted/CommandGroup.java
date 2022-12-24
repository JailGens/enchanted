package net.jailgens.enchanted;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Represents an immutable, inspectable group of subcommands.
 * <p>
 * This class represents the commands that are created via
 * {@link CommandFactory#createCommand(Object)} or {@link CommandManager#registerCommand(Object)}.
 *
 * @author Sparky983
 * @since 0.1.0
 */
public interface CommandGroup extends Subcommand {

    /**
     * Returns a list of this command group's default command's parameters.
     *
     * @return this command group's default command's parameters.
     * @since 0.1.0
     */
    @Override
    @NotNull @Unmodifiable List<? extends @NotNull CommandParameter<? extends @NotNull Object>>
    getParameters();

    /**
     * Gets a subcommand with a name that matches the specified label, or contains an alias that
     * matches the specified label.
     * <p>
     * A command name, or alias matches the label as defined by
     * {@link String#equalsIgnoreCase(String)}.
     * <p>
     * If the specified label does not match {@link Command#NAME_PATTERN}, {@code Optional.empty()}
     * is returned.
     *
     * @param label the label.
     * @return an optional containing the command that was found, or {@code Optional.empty()}.
     * @throws NullPointerException if the label is {@code null}
     * @since 0.1.0
     */
    @Contract(pure = true)
    @NotNull Optional<? extends @NotNull Subcommand> getCommand(@Pattern(NAME_PATTERN) @NotNull String label);

    /**
     * Gets all the subcommands of this group.
     * <p>
     * The returned list is an unmodifiable collection of subcommands.
     * <p>
     * All subcommands are guaranteed to be either {@link CommandGroup} or an {@link Subcommand}.
     *
     * @return all the subcommands of this group.
     * @since 0.1.0
     */
    @Contract(pure = true)
    @NotNull @Unmodifiable Collection<? extends @NotNull Subcommand> getSubcommands();
}
