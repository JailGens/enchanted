package net.jailgens.enchanted.annotations;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static net.jailgens.enchanted.Command.DESCRIPTION_PATTERN;

/**
 * Specifies the description of a command.
 *
 * @author Sparky983
 * @since 0.0.0
 */
@Documented
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface Description {

    /**
     * The description.
     *
     * @return the description.
     * @since 0.0.0
     */
    @Contract(pure = true)
    @Pattern(DESCRIPTION_PATTERN) @NotNull String value();
}
