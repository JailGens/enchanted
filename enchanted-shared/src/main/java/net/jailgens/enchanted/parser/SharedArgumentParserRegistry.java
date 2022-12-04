package net.jailgens.enchanted.parser;

import net.jailgens.enchanted.ArgumentParser;
import net.jailgens.enchanted.ArgumentParserRegistry;
import net.jailgens.enchanted.annotations.Join;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The shared implementation of {@link ArgumentParserRegistry}.
 *
 * @author Sparky983
 */
public final class SharedArgumentParserRegistry implements ArgumentParserRegistry {

    private final @NotNull Map<@NotNull Class<? extends @NotNull Annotation>, @NotNull ArgumentParser<? extends @NotNull Object>> argumentParsers = new HashMap<>();

    public SharedArgumentParserRegistry() {

        argumentParsers.put(Join.class, new JoinArgumentParser());
    }

    @Override
    public <T extends @NotNull Annotation> void registerArgumentParser(
            final @NotNull Class<@NotNull T> annotationType,
            final @NotNull ArgumentParser<@NotNull T> argumentParser) {

        argumentParsers.put(annotationType, argumentParser);
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull <T extends @NotNull Annotation> Optional<
            ? extends @NotNull ArgumentParser<@NotNull T>>
    getArgumentParser(final @NotNull Class<@NotNull T> annotationType) {

        return Optional.ofNullable((ArgumentParser<T>) argumentParsers.get(annotationType));
    }
}
