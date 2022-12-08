package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Completes arguments.
 *
 * @author Sparky983
 * @since 0.1.0
 */
@FunctionalInterface
public interface TabCompleter<T extends @NotNull Object> {

    /**
     * Creates a new empty tab completer.
     * <p>
     * This tab completer returns an empty list.
     *
     * @return a new empty tab completer.
     * @param <T> the type of the tab completer.
     * @since 0.1.0
     */
    @Contract(value = "-> new", pure = true)
    static <T extends @NotNull Object> @NotNull TabCompleter<@NotNull T> empty() {

        return (arg) -> List.of();
    }

    /**
     * Completes the specified string to an object of the specified type.
     *
     * @param string the start of the argument.
     * @return an optional containing the completed object or {@code Optional.empty()} if the string
     * was unable to be completed.
     * @throws NullPointerException the converter may optionally throw this exception if the string
     * is {@code null} (optional).
     * @since 0.1.0
     */
    @NotNull List<@NotNull String> complete(@NotNull String string);
}
