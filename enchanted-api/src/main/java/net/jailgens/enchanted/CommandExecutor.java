package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

/**
 * Something that can execute (run) commands.
 * <p>
 * Not to be confused with bukkit's command executor.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface CommandExecutor {

    /**
     * Sends the specified message to the command executor.
     *
     * @param message the message.
     * @throws NullPointerException if the message is {@code null}.
     * @since 0.0.0
     */
    void sendMessage(@NotNull String message);
}
