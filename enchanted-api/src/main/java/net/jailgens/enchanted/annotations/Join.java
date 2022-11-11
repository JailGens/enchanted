package net.jailgens.enchanted.annotations;

import net.jailgens.enchanted.CommandResult;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Joins all the rest of the arguments into a single string.
 * <p>
 * If no other arguments are after this parameter, a {@link CommandResult#error(String)} will be
 * returned.
 *
 * @author Sparky983
 * @since 0.0.0
 */
@Documented
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Join {

    /**
     * The delimiter to use.
     *
     * @return the delimiter.
     * @since 0.0.0
     */
    String value() default " ";
}
