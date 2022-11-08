package net.jailgens.enchanted.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Marks a parameter as optional. If the parameter is not present, it will be {@code null}.
 *
 * @author Sparky983
 * @since 0.0.0
 */
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Optional {

}
