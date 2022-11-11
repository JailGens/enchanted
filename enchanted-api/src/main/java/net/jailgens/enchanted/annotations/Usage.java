package net.jailgens.enchanted.annotations;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Specifies the usage of the annotated method, or class.
 *
 * @author Sparky983
 * @since 0.0.0
 */
@Documented
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface Usage {

    /**
     * The usage.
     *
     * @return the usage.
     * @since 0.0.0
     */
    @Contract(pure = true)
    @NotNull String value();
}
