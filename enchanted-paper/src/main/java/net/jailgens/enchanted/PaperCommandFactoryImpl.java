package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A {@link CommandFactory} decorator that adapts commands to paper commands.
 *
 * @author Sparky983
 * @see PaperAdapter
 */
final class PaperCommandFactoryImpl implements PaperCommandFactory {

    private final @NotNull CommandFactory commandFactory;
    private final @NotNull PaperAdapter adapter;

    @Contract(pure = true)
    PaperCommandFactoryImpl(final @NotNull CommandFactory commandFactory,
                            final @NotNull PaperAdapter adapter) {

        Objects.requireNonNull(commandFactory, "commandFactory cannot be null");
        Objects.requireNonNull(adapter, "adapter cannot be null");

        this.commandFactory = commandFactory;
        this.adapter = adapter;
    }

    @Override
    public @NotNull PaperCommandGroup createCommand(final @NotNull Object command) {

        Objects.requireNonNull(command, "command cannot be null");

        return adapter.adapt(commandFactory.createCommand(command));
    }
}
