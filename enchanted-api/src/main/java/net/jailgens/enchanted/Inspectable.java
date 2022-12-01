package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

/**
 * Represents an executable with inspectable parameters.
 *
 * @author Sparky983
 * @since 0.0
 */
public interface Inspectable extends Executable {

    /**
     * Inspects this executable's parameters, returning the result.
     * <p>
     * Any command that implements this interface must be executable with the provided parameters
     * from this method.
     * <p>
     * For example if this method returns
     * {@code [CommandParameter(name=..., isOptional=false, ...),
     * CommandParameter(name=..., isOptional=true, ...)]},
     * {@code this.execute(..., List.of("1", "2")} and
     * {@code this.execute(..., List.of("1")} must be parsable.
     *
     * @return this executable's parameters.
     * @since 0.0
     */
    @Contract(pure = true)
    @NotNull @Unmodifiable List<? extends @NotNull CommandParameter> getParameters();
}
