package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Spliterator;

public final class ListArguments implements Arguments {

    private final LinkedList<String> arguments;

    public ListArguments(final List<String> arguments) {

        this.arguments = new LinkedList<>(arguments);
    }

    @Override
    public @Range(from = 0, to = Integer.MAX_VALUE) int size() {

        return arguments.size();
    }

    @Override
    public boolean isEmpty() {

        return arguments.isEmpty();
    }

    @Override
    public @NotNull Optional<@NotNull String> pop() {

        try {
            return Optional.of(arguments.pop());
        } catch (final NoSuchElementException __) {
            return Optional.empty();
        }
    }

    @Override
    public @NotNull Optional<@NotNull String> peek() {

        return Optional.ofNullable(arguments.peek());
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull Optional<@NotNull String> peek(
            final @Range(from = 0, to = Integer.MAX_VALUE) int indexFromTop) {

        if (indexFromTop < 0) {
            throw new IllegalArgumentException("indexFromTop cannot be negative");
        }

        if (indexFromTop >= size()) {
            return Optional.empty();
        }

        return Optional.of(arguments.get(indexFromTop));
    }

    @Override
    public @NotNull Iterator<@NotNull String> iterator() {

        // make list unmodifiable so Iterator.remove throws UnsupportedOperationException
        return Collections.unmodifiableList(arguments).iterator();
    }

    @Override
    public Spliterator<@NotNull String> spliterator() {

        return arguments.spliterator();
    }
}
