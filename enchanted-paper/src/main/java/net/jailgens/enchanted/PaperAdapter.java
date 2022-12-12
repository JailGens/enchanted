package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Adapts normal enchanted-api types to enchanted-paper types.
 *
 * @author Sparky983
 */
interface PaperAdapter {

    /**
     * Adapts a normal {@link Command} to a {@link PaperCommand}.
     *
     * @param command the command.
     * @return the paper command group.
     * @throws NullPointerException if the group is {@code null}.
     */
    @Contract(pure = true)
    @NotNull PaperCommand adapt(@NotNull Command command);

    /**
     * Adapts a normal {@link Subcommand} to a {@link PaperSubcommand}.
     *
     * @param subcommand the subcommand.
     * @return the paper subcommand.
     * @throws NullPointerException if the subcommand is {@code null}.
     */
    @Contract(pure = true)
    @NotNull PaperSubcommand adapt(@NotNull Subcommand subcommand);

    /**
     * Adapts a normal {@link CommandGroup} to a {@link PaperCommandGroup}.
     *
     * @param group the group.
     * @return the paper command group.
     * @throws NullPointerException if the group is {@code null}.
     */
    @Contract(pure = true)
    @NotNull PaperCommandGroup adapt(@NotNull CommandGroup group);

    /**
     * Adapts a normal {@link CommandParameter} to a {@link PaperCommandParameter}.
     *
     * @param parameter the parameter.
     * @return the paper command parameter.
     * @throws NullPointerException if the parameter is {@code null}.
     */
    @Contract(pure = true)
    <T extends @NotNull Object> @NotNull PaperCommandParameter<@NotNull T> adapt(
            @NotNull CommandParameter<@NotNull T> parameter);
}
