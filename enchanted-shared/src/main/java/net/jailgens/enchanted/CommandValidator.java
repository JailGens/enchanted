package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static net.jailgens.enchanted.Command.DESCRIPTION_PATTERN;
import static net.jailgens.enchanted.Command.NAME_PATTERN;

/**
 * A utility class for validating commands.
 *
 * @author Sparky983.
 */
@SuppressWarnings("PatternValidation")
// patterns are guaranteed to be valid because they are checked
final class CommandValidator {

    private static final @NotNull Pattern COMPILED_NAME_PATTERN = Pattern.compile(NAME_PATTERN);
    private static final @NotNull Pattern COMPILED_DESCRIPTION_PATTERN =
            Pattern.compile(DESCRIPTION_PATTERN);

    private CommandValidator() {

    }

    /**
     * Validates the given command name.
     *
     * @param name the name of the command.
     * @return the validated command.
     */
    @org.intellij.lang.annotations.Pattern(NAME_PATTERN)
    static @NotNull String validateName(final @NotNull String name) {

        Objects.requireNonNull(name, "name cannot be null");

        if (!COMPILED_NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("name must match the pattern /" + NAME_PATTERN + "/");
        }

        return name;
    }

    /**
     * Validates the given command aliases.
     *
     * @param name the name of the command.
     * @param aliases the aliases of the command.
     * @return the validated aliases.
     */
    static @NotNull List</* @org.intellij.lang.annotations.Pattern(NAME_PATTERN) */@NotNull String> validateAliases(
            final @NotNull String name,
            final @NotNull List<@NotNull String> aliases) {

        Objects.requireNonNull(aliases, "aliases cannot be null");
        Objects.requireNonNull(name, "name cannot be null");

        if (aliases.contains(name)) {
            throw new IllegalArgumentException("name cannot be an alias");
        }

        aliases.forEach(CommandValidator::validateName);

        return aliases;
    }

    @org.intellij.lang.annotations.Pattern(DESCRIPTION_PATTERN)
    static @NotNull String validateDescription(final @NotNull String description) {

        Objects.requireNonNull(description, "description cannot be null");

        if (!COMPILED_DESCRIPTION_PATTERN.matcher(description).matches()) {
            throw new IllegalArgumentException("description must match the pattern /" + DESCRIPTION_PATTERN + "/");
        }

        return description;
    }
}
