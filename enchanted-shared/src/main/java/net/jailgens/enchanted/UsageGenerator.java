package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Generates command usages.
 *
 * @author Sparky983
 */
public interface UsageGenerator {

    /**
     * Generates the usage for the specified command.
     *
     * @param command the command.
     * @return the usage.
     */
    @Contract(pure = true)
    @NotNull String generateUsage(@NotNull Command command);
}
