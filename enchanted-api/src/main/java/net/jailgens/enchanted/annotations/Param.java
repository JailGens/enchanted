package net.jailgens.enchanted.annotations;

import net.jailgens.enchanted.CommandParameter;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static net.jailgens.enchanted.CommandParameter.NAME_PATTERN;

/**
 * Specifies the name of a parameter
 *
 * @author Sparky983
 * @since 0.1.0
 */
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Param {

    /**
     * The name of the parameter.
     *
     * @return the name of the parameter.
     * @since 0.1.0
     */
    @Contract(pure = true)
    @Pattern(NAME_PATTERN)
    @NotNull String value();
}
