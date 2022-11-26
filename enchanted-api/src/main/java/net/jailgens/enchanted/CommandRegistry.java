package net.jailgens.enchanted;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Optional;

import static net.jailgens.enchanted.Command.NAME_PATTERN;

/**
 * A registry of {@link Command}s.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface CommandRegistry {

    /**
     * Registers the specified command to this registry.
     *
     * @param command the command to add.
     * @return the created command as specified in {@link CommandFactory}.
     * @throws IllegalArgumentException if the command failed validation.
     * @throws IllegalStateException if the command is currently registered to this registry or
     * another command has been registered with a name or aliases that conflict with the command.
     * @throws NullPointerException if the command is {@code null}.
     * @see #unregisterCommand(Command)
     * @since 0.0.0
     */
    @Contract(value = "_ -> new", mutates = "this")
    @NotNull Command registerCommand(@NotNull Object command);

    /**
     * Removes the specified command from this registry.
     *
     * @param command the command to remove.
     * @throws IllegalStateException if the command is not currently registered to this registry.
     * @throws NullPointerException if the command is {@code null}.
     * @see #registerCommand(Object)
     * @since 0.0.0
     */
    @Contract(mutates = "this")
    void unregisterCommand(@NotNull Command command);

    /**
     * Gets a command with a name that matches the specified label, or contains an alias that
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
     * @since 0.0.0
     */
    @NotNull Optional<? extends @NotNull Command> getCommand(@Pattern(NAME_PATTERN) @NotNull String label);

    /**
     * Gets all the registered commands of this registry.
     * <p>
     * The returned list is an unmodifiable (not unmodifiable view) collection of all registered
     * commands.
     *
     * @return all the registered commands of this registry.
     * @since 0.0.0
     */
    @NotNull @Unmodifiable Collection<? extends @NotNull Command> getRegisteredCommands();
}
