package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

/**
 * Represents a command that is capable of being used as a subcommand.
 * <p>
 * For example an {@link CommandGroup} may be used as either a top level command, or in an inner
 * class.
 *
 * @author Sparky983
 * @since 0.1.0
 */
public interface Subcommand extends Inspectable, Command {

    /**
     * Gets all this subcommand's parameters.
     *
     * @return this subcommand's parameters.
     * @since 0.1.0
     */
    @Override
    @Contract(pure = true)
    @NotNull @Unmodifiable List<? extends @NotNull CommandParameter> getParameters();
}
