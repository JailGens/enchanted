package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents anything that is executable.
 * <p>
 * Essentially an anonymous (unnamed) command.
 * <p>
 * Keep in mind that, although normal {@link Command}s are not anonymous, they inherit all behaviour
 * from this interface.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface Executable {

    /**
     * Executes this executable.
     *
     * @param sender the sender.
     * @param arguments the arguments.
     * @return the result of the execution.
     * @since 0.0.0
     */
    @NotNull CommandResult execute(@NotNull CommandExecutor sender,
                                   @NotNull List<@NotNull String> arguments);

    /**
     * Gets the usage of this command.
     * <p>
     * The returned usage, will only show the syntax for the parameters.
     * <p>
     * By default, the usage has the following syntax:
     * <pre>{@literal
     * usage :== ((param ' ')* param)?
     * name :== /[\w-]+/
     * param :== optional-param |
     *           required-param |
     *           literal-param |
     *           optional-enum-param |
     *           required-enum-param |
     *           context-required
     * optional-param :== '[' name ']'
     * required-param :== '<' name '>'
     * optional-enum-param :== '[' (name '|')* name ']'
     * required-enum-param :== '<' (name '|')* name '>'
     * literal-param :== name
     * context-required :== '...'
     * }</pre>
     * This syntax is only a convention, this method does not guarantee that the returned usage will
     * follow this syntax.
     *
     * @return the usage of this command
     * @since 0.0.0
     */
    @Contract(pure = true)
    @NotNull String getUsage();
}
