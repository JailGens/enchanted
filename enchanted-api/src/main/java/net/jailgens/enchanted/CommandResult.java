package net.jailgens.enchanted;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * The result of a command execution.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface CommandResult {

    // TODO(Sparky983) redo interface hierarchy and class hierarchy in AbstractCommandResult
    //

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
     * Creates a {@link Translate translatable} and {@link Success successful} result with the
     * specified message key.
     *
     * @param key the message key.
     * @return the result.
     * @throws NullPointerException if the key is {@code null}.
     * @see #translate(String, Map)
     * @see Success#translate(String)
     * @since 0.1.0
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull Translate translate(final @NotNull String key) {

        return Success.translate(key);
    }

    /**
     * Creates a {@link Translate translatable} and {@link Success successful} result with the
     * specified message key and placeholders.
     *
     * @param key the message key.
     * @param placeholders the message placeholders.
     * @return the result.
     * @throws NullPointerException if the key or placeholders are {@code null}.
     * @see #translate(String)
     * @see Success#translate(String, Map)
     * @since 0.1.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull Translate translate(final @NotNull String key, final @NotNull Map<@NotNull String, @NotNull Object> placeholders) {

        return Success.translate(key, placeholders);
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

        /**
         * Creates a {@link Translate translatable} and {@link Success successful} result with the
         * specified message key without any placeholders.
         *
         * @param key the message key.
         * @return the result.
         * @throws NullPointerException if the key is {@code null}.
         * @see #translate(String, Map)
         * @see CommandResult#translate(String)
         * @see Translate#success(String)
         * @since 0.1.0
         */
        @Contract(value = "_ -> new", pure = true)
        static @NotNull Translate translate(final @NotNull String key) {

            return translate(key, Map.of());
        }

        /**
         * Creates a {@link Translate translatable} and {@link Success successful} result with the
         * specified message key and placeholders.
         *
         * @param key the message key.
         * @param placeholders the placeholders.
         * @return the result.
         * @throws NullPointerException if the key or placeholders are {@code null}.
         * @see #translate(String)
         * @see CommandResult#translate(String, Map)
         * @see Translate#success(String, Map)
         * @since 0.1.0
         */
        @Contract(value = "_, _ -> new", pure = true)
        static @NotNull Translate translate(final @NotNull String key,
                                            final @NotNull Map<String, Object> placeholders) {

            return new AbstractCommandResult.TranslateSuccessImpl(key, placeholders);
        }
    }

    /**
     * Currently just a marker interface for exceptional results.
     *
     * @since 0.0.0
     */
    interface Error extends CommandResult {

        /**
         * Creates a {@link Translate translatable} and {@link Error exceptional} result with the
         * specified message key without any placeholders.
         *
         * @param key the message key.
         * @return the result.
         * @throws NullPointerException if the key is {@code null}.
         * @see #translate(String, Map)
         * @see Translate#error(String)
         * @since 0.1.0
         */
        @Contract(value = "_ -> new", pure = true)
        static @NotNull Translate translate(final @NotNull String key) {

            return translate(key, Map.of());
        }

        /**
         * Creates a {@link Translate translatable} and {@link Error exceptional} result with the
         * specified message key and placeholders.
         *
         * @param key the message key.
         * @param placeholders the placeholders.
         * @return the result.
         * @throws NullPointerException if the key or placeholders are {@code null}.
         * @see #translate(String)
         * @see Translate#error(String, Map)
         * @since 0.1.0
         */
        @Contract(value = "_, _ -> new", pure = true)
        static @NotNull Translate translate(final @NotNull String key,
                                            final @NotNull Map<String, Object> placeholders) {

            return new AbstractCommandResult.TranslateErrorImpl(key, placeholders);
        }
    }

    /**
     * Represents a translatable result.
     *
     * @since 0.1.0
     */
    interface Translate extends CommandResult {

        /**
         * Creates a {@link Translate translatable} and {@link Success successful} result with the
         * specified message key without any placeholders.
         *
         * @param key the message key.
         * @return the result.
         * @throws NullPointerException if the key is {@code null}.
         * @see #translate(String, Map)
         * @see Success#translate(String)
         * @since 0.1.0
         */
        @Contract(value = "_ -> new", pure = true)
        static @NotNull Translate success(final @NotNull String key) {

            return Success.translate(key);
        }

        /**
         * Creates a {@link Translate translatable} and {@link Success successful} result with the
         * specified message key and placeholders.
         *
         * @param key the message key.
         * @param placeholders the message placeholders.
         * @return the result.
         * @throws NullPointerException if the key or placeholders are {@code null}.
         * @see #translate(String)
         * @see Success#translate(String, Map)
         * @since 0.1.0
         */
        @Contract(value = "_, _ -> new", pure = true)
        static @NotNull Translate success(
                final @NotNull String key,
                final @NotNull Map<@NotNull String, @NotNull Object> placeholders) {

            return Success.translate(key, placeholders);
        }

        /**
         * Creates a {@link Translate translatable} and {@link Error exceptional} result with the
         * specified message key without any placeholders.
         *
         * @param key the message key.
         * @return the result.
         * @throws NullPointerException if the key is {@code null}.
         * @see #translate(String, Map)
         * @see Error#translate(String)
         * @since 0.1.0
         */
        @Contract(value = "_ -> new", pure = true)
        static @NotNull Translate error(final @NotNull String key) {

            return Error.translate(key);
        }

        /**
         * Creates a {@link Translate translatable} and {@link Error exceptional} result with the
         * specified message key and placeholders.
         *
         * @param key the message key.
         * @param placeholders the message placeholders.
         * @return the result.
         * @throws NullPointerException if the key or placeholders are {@code null}.
         * @see #translate(String)
         * @see Error#translate(String, Map)
         * @since 0.1.0
         */
        @Contract(value = "_, _ -> new", pure = true)
        static @NotNull Translate error(final @NotNull String key,
                                        final @NotNull Map<@NotNull String, @NotNull Object> placeholders) {

            return Error.translate(key, placeholders);
        }

        /**
         * Returns the translation key.
         *
         * @return the translation key.
         * @since 0.1.0
         */
        @Contract(pure = true)
        @NotNull String getKey();

        /**
         * Returns the translation placeholders.
         *
         * @return the translation placeholders.
         * @since 0.1.0
         */
        @Contract(pure = true)
        @NotNull @Unmodifiable Map<@NotNull String, @NotNull Object> getPlaceholders();
    }
}
