package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

/**
 * A paper {@link CommandFactory}
 *
 * @author Sparky983
 * @since 0.1.0
 */
public interface PaperCommandFactory extends CommandFactory {

    @Override
    @NotNull PaperCommandGroup createCommand(@NotNull Object command);
}
