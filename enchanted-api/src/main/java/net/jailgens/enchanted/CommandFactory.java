package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Aliases;
import net.jailgens.enchanted.annotations.Description;
import net.jailgens.enchanted.annotations.Join;
import net.jailgens.enchanted.annotations.Optional;
import net.jailgens.enchanted.annotations.Usage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A command factory that turns user-defined annotated commands into usable
 * {@link net.jailgens.enchanted.Command}s.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface CommandFactory {

    /**
     * Creates a command from the specified user-defined command.
     * <p>
     * This command will produce the command by reading the meta-data annotations defined in
     * {@link net.jailgens.enchanted.annotations} package from the specified command.
     * <p>
     * The resulting command should:
     * <ul>
     *     <li>Be named with the {@link net.jailgens.enchanted.annotations.Command#value() value}
     *     of the {@link net.jailgens.enchanted.annotations.Command} annotation that the specified
     *     command is annotated with. If the annotation is not present, an
     *     {@link IllegalArgumentException} is thrown.</li>
     *     <li>Have the aliases from {@link Aliases#value()} or {@code {}}</li>
     *     <li>Contain the usage from {@link Usage#value}. If the annotation is not present on the
     *     command, the factory may choose an alternative</li>
     *     <li>Have the description from {@link Description#value()} or {@code ""}</li>
     *     <li>Have sub-commands of all methods, and nested classes (command groups) annotated with
     *     {@link net.jailgens.enchanted.annotations.Command}. The way methods created is defined
     *     <a href="methods">here</a></li>
     *     <li>The {@link Command#execute(CommandExecutor, List)} method invokes the sub-command with the
     *     label {@code arguments[0]}'s {@link Command#execute(CommandExecutor, List)} with a sub-list
     *     without the first ({@code arguments[0]}) argument. If no sub-command was found, the
     *     default command (marked with {@link net.jailgens.enchanted.annotations.Command.Default}
     *     is invoked with {@code arguments}, and if no default command exists, an error message
     *     is sent to the sender.</li>
     * </ul>
     * <p>
     * Command groups are just nested classes of the root-command or another command group that are
     * annotated with the {@link net.jailgens.enchanted.annotations.Command} annotation. If the
     * nested class is static, it may construct them normally, but if it is not the enclosing
     * instance argument must be the specified command (or the enclosing command group instance).
     * The returned {@link net.jailgens.enchanted.annotations.Command} object for the nested class
     * follow the same restrictions as ones created with this method.
     * <p>
     * <h2><a id="methods">Methods</a></h2>
     * Each method annotated with {@link net.jailgens.enchanted.annotations.Command} is turned into
     * a sub-command. The returned {@link net.jailgens.enchanted.annotations.Command sub-command}
     * of each method follows the same contract as for root-commands except:
     * <ul>
     *     <li>The {@link Command#execute(CommandExecutor, List)} method calls the method with the argument
     *     resolvers applied to each argument depending on the method's parameter.</li>
     *     <li>Parameters annotated with {@link Join} be the last parameter, or 
     *     {@link IllegalArgumentException} will be thrown. The {@link Join} annotation makes consumes all the rest of
     *     the input arguments, and joins them by the {@link Join#value()} delimiter.</li>
     *     <li>Parameters annotated with {@link Optional} are not required, and if no argument for 
     *     that parameter exists, the value will be {@code null}. No other required parameters may
     *     follow an optional parameter. Required parameters are parameters that aren't annotated 
     *     with {@link Join} or {@link Optional}.</li>
     * </ul>
     *
     * @param command the user-defined command.
     * @return the created command.
     * @throws IllegalArgumentException if the command failed validation.
     * @throws NullPointerException if the command is {@code null}.
     * @since 0.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    @NotNull Command createCommand(@NotNull Object command);
}
