package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Optional;

/**
 * A paper {@link CommandGroup}.
 *
 * @author Sparky983
 * @since 0.1.0
 */
public interface PaperCommandGroup extends CommandGroup, PaperSubcommand {

    @Override
    @NotNull Optional<? extends @NotNull PaperSubcommand> getCommand(@NotNull String label);

    @Override
    @NotNull @Unmodifiable Collection<? extends @NotNull PaperSubcommand> getSubcommands();
}
