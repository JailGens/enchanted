package net.jailgens.enchanted;

import net.jailgens.mirror.Method;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Creates method commands.
 *
 * @author Sparky983
 */
public interface MethodCommandFactory {

    /**
     * Creates a method command for the specified method.
     *
     * @param command the command instance.
     * @param method the method.
     * @return the method command.
     * @throws NullPointerException if the method is {@code null}.
     */
    @Contract(value = "_, _ -> new", pure = true)
    <T extends @NotNull Object> @NotNull Command createCommand(@NotNull T command, @NotNull Method<? extends @NotNull T, ? extends @NotNull Object> method);
}
