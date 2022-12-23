package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

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
     * Sends a translated message to the command executor.
     *
     * @param key the translation key.
     * @param placeholders the message placeholders.
     * @throws NullPointerException if the key or arguments are {@code null}.
     * @see TranslationKey
     * @since 0.1.0
     */
    void sendTranslatedMessage(
            @NotNull String key, @NotNull Map<@NotNull String, @NotNull Object> placeholders);

    /**
     * Gets an alternative executor that doesn't have to implement {@link CommandExecutor}.
     * <p>
     * This can be used so consumers can use other command executors such as Bukkit's
     * {@code CommandSender} interface.
     * <p>
     * If there is no alternative executor, it is valid to return {@code this}.
     *
     * @return the alternative executor.
     * @since 0.1.0
     */
    @NotNull Object getAlternativeExecutor();
}
