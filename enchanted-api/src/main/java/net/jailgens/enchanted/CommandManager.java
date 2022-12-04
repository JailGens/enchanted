package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The main manager for all commands.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface CommandManager extends CommandFactory,
        CommandRegistry,
        ConverterRegistry,
        ArgumentParserRegistry,
        ArgumentResolverRegistry {

    /**
     * Registers the specified command to this registry.
     *
     * @param command the command.
     * @return the created command as specified in {@link CommandFactory}.
     * @throws IllegalArgumentException if the command failed validation.
     * @throws IllegalStateException if the command is currently registered to this registry or
     * another command has been registered with a name or aliases that conflict with the command.
     * @throws NullPointerException if the command is {@code null}.
     * @see #registerCommand(Command)
     * @see #unregisterCommand(Command)
     * @since 0.0.0
     */
    @Contract(value = "_ -> new", mutates = "this")
    @NotNull CommandGroup registerCommand(@NotNull Object command);
}
