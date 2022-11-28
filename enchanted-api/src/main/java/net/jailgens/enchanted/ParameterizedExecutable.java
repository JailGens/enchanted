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
public interface ParameterizedExecutable extends Executable {

    /**
     * Gets all this executable's parameters.
     *
     * @return this executable's parameters.
     * @since 0.0
     */
    @Contract(pure = true)
    @NotNull @Unmodifiable List<? extends @NotNull CommandParameter> getParameters();
}
