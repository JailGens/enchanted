package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents anything that is executable.
 * <p>
 * Essentially an anonymous (unnamed) command.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface Executable {

    /**
     * Executes this executable.
     *
     * @param sender the sender.
     * @param arguments the arguments.
     * @return the result of the execution.
     * @since 0.0.0
     */
    @NotNull CommandResult execute(@NotNull CommandExecutor sender,
                                   @NotNull List<@NotNull String> arguments);
}
