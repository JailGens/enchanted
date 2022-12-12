package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

/**
 * A paper {@link CommandRegistry}.
 *
 * @author Sparky983
 * @since 0.1.0
 */
public interface PaperCommandRegistry extends CommandRegistry {

    @NotNull PaperCommand registerCommand(@NotNull PaperCommand command);

    @Override
    @NotNull PaperCommand registerCommand(@NotNull Command command);
}
