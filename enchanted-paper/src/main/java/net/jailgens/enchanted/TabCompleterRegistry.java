package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A registry of {@link TabCompleter}s.
 *
 * @author Sparky983
 * @since 0.1.0
 */
public interface TabCompleterRegistry {

    /**
     * Registers a tab completer.
     *
     * @param type the type of parameter to use the completer.
     * @param completer the tab completer.
     * @param <T> the type of parameter.
     * @throws NullPointerException if the type or the tab completer is {@code null}.
     * @since 0.1.0
     */
    @Contract(mutates = "this")
    <T extends @NotNull Object> void registerTabCompleter(@NotNull Class<@NotNull T> type,
                                              @NotNull TabCompleter<@NotNull T> completer);

    /**
     * Gets a tab completer of the specified type.
     * <p>
     * If no completer are registered for the specified type, this method returns a completer that
     * returns an empty list.
     *
     * @param <T> the type of the tab completer.
     * @param type the type of the tab completer.
     * @return the tab completer.
     * @throws NullPointerException if the type is {@code null}.
     * @since 0.1.0
     */
    <T extends @NotNull Object> @NotNull TabCompleter<@NotNull T> getTabCompleter(
            @NotNull Class<T> type);
}
