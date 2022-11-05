package net.jailgens.enchanted;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

/**
 * The result of a command execution.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface CommandResult {

    /**
     * Gets an empty success result.
     * <p>
     * No message will be sent to the executor upon handling of the result.
     *
     * @return an empty success result.
     * @since 0.0.0
     */
    @Contract(pure = true)
    static @NotNull Success success() {

        return AbstractCommandResult.SuccessImpl.SUCCESS;
    }

    /**
     * Creates a {@link Success successful} result with the specified message.
     * <p>
     * The message will be sent to the command executor upon handling of the result.
     *
     * @param message the message.
     * @return the result.
     * @throws NullPointerException if the message is {@code null}.
     * @since 0.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull Success success(final @NotNull String message) {

        Objects.requireNonNull(message, "message cannot be null");

        return new AbstractCommandResult.SuccessImpl(message);
    }

    /**
     * Creates a {@link Error exeptional} result with the specified message.
     * <p>
     * The message will be sent to the command executor upon handling of the result.
     *
     * @param message the message.
     * @return the result.
     * @throws NullPointerException if the message is {@code null}.
     * @since 0.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull Error error(final @NotNull String message) {

        Objects.requireNonNull(message, "message cannot be null");

        return new AbstractCommandResult.ErrorImpl(message);
    }

    /**
     * Returns whether the command was executed successfully.
     *
     * @return whether the command was executed successfully.
     * @since 0.0.0
     */
    boolean isSuccess();

    /**
     * Returns whether the command was executed exceptionally.
     *
     * @return whether the command was executed exceptionally.
     * @since 0.0.0
     */
    boolean isError();

    /**
     * Returns an optional containing a message that will be sent to the executor.
     * <p>
     * The command manager will not send this, the implementation of
     * {@link #handleResult(CommandExecutor)} must do this.
     *
     * @return an optional containing a message that will be sent to the executor.
     * @since 0.0.0
     */
    @NotNull Optional<@NotNull String> getMessage();

    /**
     * Handles the result for the given command executor.
     *
     * @param executor the executor.
     * @throws IllegalArgumentException if the result cannot be displayed to the executor.
     * @throws NullPointerException if the executor is {@code null}.
     * @since 0.0.0
     */
    @ApiStatus.OverrideOnly
    // should only be called by the command manager
    void handleResult(@NotNull CommandExecutor executor);

    /**
     * Currently just a marker interface for successful results.
     *
     * @since 0.0.0
     */
    interface Success extends CommandResult {

    }

    /**
     * Currently just a marker interface for exceptional results.
     *
     * @since 0.0.0
     */
    interface Error extends CommandResult {

    }
}
