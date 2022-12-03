package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * A checked exception that is thrown when an exception occurs while parsing an argument.
 *
 * @author Sparky983
 * @since 0.1.0
 */
public final class ArgumentParseException extends Exception {

    /**
     * Constructs a new argument parse exception.
     *
     * @since 0.1.0
     */
    @Contract(pure = true)
    public ArgumentParseException() {

    }

    /**
     * Constructs a new argument parse exception with the specified detail message.
     *
     * @param message the detail message.
     * @since 0.1.0
     */
    @Contract(pure = true)
    public ArgumentParseException(final @Nullable String message) {

        super(message);
    }

    /**
     * Constructs a new argument parse exception with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause.
     * @since 0.1.0
     */
    @Contract(pure = true)
    public ArgumentParseException(final @Nullable String message,
                                  final @Nullable Throwable cause) {

        super(message, cause);
    }

    /**
     * Constructs a new argument parse exception with the specified cause.
     *
     * @param cause the cause.
     * @since 0.1.0
     */
    @Contract(pure = true)
    public ArgumentParseException(final @Nullable Throwable cause) {

        super(cause);
    }
}
