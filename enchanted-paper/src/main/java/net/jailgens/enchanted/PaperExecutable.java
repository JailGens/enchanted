package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A paper {@link Executable}.
 *
 * @author Sparky983
 * @since 0.1.0
 */
public interface PaperExecutable extends Executable {

    /**
     * Completes the last argument of the given arguments.
     *
     * @param sender the sender of the command.
     * @param arguments the arguments of the command.
     * @return the completions of the last argument.
     * @since 0.1.0
     */
    @NotNull List<@NotNull String> complete(@NotNull CommandExecutor sender,
                                            @NotNull List<@NotNull String> arguments);
}
