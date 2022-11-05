package net.jailgens.enchanted.annotations;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Specifies the aliases of a command.
 *
 * @author Sparky983
 * @since 0.0.0
 */
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface Aliases {

    /**
     * The aliases.
     *
     * @return the aliases.
     * @since 0.0.0
     */
    @Contract(pure = true)
    /* @Pattern(Command.NAME_PATTERN) */ @NotNull String @NotNull [] value();
}
