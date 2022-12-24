package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

/**
 * A utility class containing all default translation keys.
 *
 * @author Sparky983
 * @since 0.1.0
 */
public final class TranslationKey {

    /**
     * The translation key for the "invalid usage" message.
     * <p>
     * Has the following placeholders:
     * <ul>
     *     <li>{@code usage} - the usage of the command.</li>
     * </ul>
     *
     * @since 0.1.0
     */
    public static final @NotNull String INVALID_USAGE = "enchanted.invalid-usage";

    /**
     * The translation key for the "invalid sender" message.
     * <p>
     * Has the following placeholders:
     * <ul>
     *     <li>{@code sender} - the sender of the command.</li>
     * </ul>
     *
     * @since 0.1.0
     */
    public static final @NotNull String INVALID_SENDER = "enchanted.invalid-sender";

    /**
     * The translation key for the "invalid argument" message.
     * <p>
     * Has the following placeholders:
     * <ul>
     *     <li>{@code argument} - the argument that was invalid.</li>
     *     <li>{@code reason} - the reason the argument was invalid.</li>
     *     <li>{@code usage} - the usage of the command.</li>
     * </ul>
     *
     * @see #GENERIC_INVALID_ARGUMENT
     * @since 0.1.0
     */
    public static final @NotNull String INVALID_ARGUMENT = "enchanted.invalid-argument";

    /**
     * The translation key for the "generic invalid argument" message.
     * <p>
     * This is similar to {@link #INVALID_ARGUMENT} except it does not have a {@code reason}
     * placeholder.
     * <p>
     * Has the following placeholders:
     * <ul>
     *     <li>{@code argument} - the argument that was invalid.</li>
     *     <li>{@code usage} - the usage of the command.</li>
     * </ul>
     *
     * @see #INVALID_ARGUMENT
     * @since 0.1.0
     */
    public static final @NotNull String GENERIC_INVALID_ARGUMENT = "enchanted.generic-invalid-argument";

    /**
     * The translation key for the "internal error" message.
     * <p>
     * No placeholders.
     *
     * @since 0.1.0
     */
    public static final @NotNull String INTERNAL_ERROR = "enchanted.internal-error";

    private TranslationKey() {

    }
}
