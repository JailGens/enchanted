package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

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
     */
    static void validateName(final @NotNull String name) {

        Objects.requireNonNull(name, "name cannot be null");

        if (!COMPILED_NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("name must match the pattern /" + NAME_PATTERN + "/");
        }
    }
}
