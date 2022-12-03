package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Iterator;
import java.util.Optional;

/**
 * Represents a stack (first in, first out) of command arguments.
 * <p>
 * This interface is only consumed by the user.
 *
 * @author Sparky983
 * @since 0.1.0
 */
public interface Arguments extends Iterable<@NotNull String> {

    // TODO(Sparky983): consider whether to make this a sub-interface of Collection

    /**
     * Gets the total amount of arguments left.
     *
     * @return the total amount of arguments left.
     * @since 0.1.0
     */
    @Range(from = 0, to = Integer.MAX_VALUE) int size();

    /**
     * Checks whether the stack is empty ({@code size() == 0}).
     *
     * @return whether the stack is empty.
     * @since 0.1.0
     */
    boolean isEmpty();

    /**
     * Pops off the top argument from the stack and returns it
     * <p>
     * Examples:
     * <pre>{@code
     * // arguments for /<command> 1 2
     * Arguments arguments = {
     *         1 // top
     *         2
     * };
     * arguments.pop(); // returns Optional[1]
     * arguments.pop(); // returns Optional[2]
     * arguments.pop(); // returns Optional.empty
     * }</pre>
     *
     * @return an optional containing the top argument otherwise {@code Optional.empty()} if there
     * are no arguments left.
     * @since 0.1.0
     */
    @Contract(mutates = "this")
    @NotNull Optional<@NotNull String> pop();

    /**
     * Peeks at the top argument of the stack and returns it
     *
     * @return an optional containing the top argument otherwise {@code Optional.empty()} if there
     * are no arguments left.
     * @since 0.1.0
     */
    @NotNull Optional<@NotNull String> peek();

    /**
     * Peeks at the element at the specified index of from the top of the stack and returns it.
     * <p>
     * Examples:
     * <pre>{@code
     * // arguments for command /<command> 1 2
     * Arguments arguments = {
     *         1 // top
     *         0
     * };
     * arguments.peek(0); // returns Optional[1]
     * arguments.peek(1); // returns Optional[0]
     * arguments.peek(2); // returns Optional.empty
     * }</pre>
     *
     * @return an optional containing the top argument otherwise {@code Optional.empty()} if
     * {@code size() - indexFromTop <= 0}.
     * @throws IllegalArgumentException if {@code indexFromTop < 0}.
     * @since 0.1.0
     */
    @NotNull Optional<@NotNull String> peek(@Range(from = 0, to = Integer.MAX_VALUE) int indexFromTop);

    /**
     * Gets an iterator over the arguments from top to bottom.
     *
     * @return an iterator over the arguments from top to bottom.
     */
    @Override
    @NotNull Iterator<@NotNull String> iterator();
}
