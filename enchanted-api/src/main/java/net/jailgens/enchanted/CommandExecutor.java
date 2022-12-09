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

    /**
     * Gets an alternative executor that doesn't have to implement {@link CommandExecutor}.
     * <p>
     * This can be used so consumers can use other command executors such as Bukkit's
     * {@code CommandSender} interface.
     * <p>
     * If there is no alternative executor, it is valid to return {@code this}.
     *
     * @return the alternative executor.
     */
    @NotNull Object getAlternativeExecutor();
}
