package net.jailgens.enchanted.resolver;

import net.jailgens.enchanted.ArgumentParseException;
import net.jailgens.enchanted.ArgumentParser;
import net.jailgens.enchanted.ArgumentResolver;
import net.jailgens.enchanted.Arguments;
import net.jailgens.enchanted.CommandParameter;
import net.jailgens.enchanted.parser.CommandParameterParser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sn {@link ArgumentResolver} that isn't dependent on an annotation.
 * <p>
 * This has two implementations: one for the default argument resolver (no annotation) and the other
 * by adapting {@link ArgumentResolver}s (with annotation) to this.
 *
 * @author Sparky983
 * @see CommandParameterParser
 */
public interface CommandParameterResolver {

    @NotNull CommandParameterResolver DEFAULT = new DefaultCommandParameterResolver();

    /**
     * Resolves the specified argument into an object of type {@link T}.
     *
     * @param parameter the command parameter.
     * @param type the type of the object.
     * @param arguments the arguments after they have been parsed with an {@link ArgumentParser}.
     * @return the resolved object.
     * @param <T> the type of the object.
     * @throws ArgumentParseException if the argument could not be parsed due to the arguments being
     * unparsable.
     * @throws NullPointerException if the type or arguments is {@code null} (optional).
     * @since 0.1.0
     */
    <T extends @Nullable Object> /*@NotNull*/ T resolve(@NotNull CommandParameter<T> parameter,
                                                        @NotNull Class<@NotNull T> type,
                                                        @NotNull Arguments arguments)
            throws ArgumentParseException;
}
