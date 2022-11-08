package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

/**
 * A usage generator that generates no usage.
 *
 * @author Sparky983
 */
final class EmptyUsageGenerator implements UsageGenerator {

    @Override
    public @NotNull String generateUsage(final @NotNull Command command) {

        return "";
    }
}
