package net.jailgens.enchanted.annotations;

import net.jailgens.enchanted.CommandFactory;
import org.intellij.lang.annotations.Pattern;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static net.jailgens.enchanted.Command.NAME_PATTERN;

/**
 * Marks a method, or class as a command for the {@link CommandFactory}.
 *
 * @author Sparky983
 * @since 0.0.0
 */
@Retention(RUNTIME)
@Target({METHOD, TYPE})
public @interface Command {

    /**
     * The name of the command.
     *
     * @return the name of the command.
     * @since 0.0.0
     */
    @Contract(pure = true)
    @Pattern(NAME_PATTERN) @NotNull String value();

    /**
     * Marks a method as a default command for the {@link CommandFactory}.
     *
     * @since 0.0.0
     */
    @Retention(RUNTIME)
    @Target({METHOD})
    @interface Default {

    }
}
