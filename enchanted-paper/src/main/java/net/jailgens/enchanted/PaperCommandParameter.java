package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

/**
 * A paper {@link CommandParameter}.
 *
 * @author Sparky983
 * @param <T> {@inheritDoc}
 * @since 0.1.0
 */
public interface PaperCommandParameter<T extends @NotNull Object>
        extends CommandParameter<@NotNull T> {

    /**
     * Gets this parameter's tab completer.
     * <p>
     * If none is specified, this the tab completer returns an empty list.
     *
     * @return this parameter's tab completer.
     * @since 0.1.0
     */
    @NotNull TabCompleter<@NotNull T> getTabCompleter();
}
