package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Aliases;
import net.jailgens.enchanted.annotations.Command;
import net.jailgens.enchanted.annotations.Description;
import net.jailgens.mirror.AnnotationElement;
import net.jailgens.mirror.AnnotationValues;
import net.jailgens.mirror.Mirror;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static net.jailgens.enchanted.Command.DESCRIPTION_PATTERN;
import static net.jailgens.enchanted.Command.NAME_PATTERN;

/**
 * A {@link Mirror} based implementation of {@link CommandInfo}.
 *
 * @author Spark983
 */
@SuppressWarnings("PatternValidation") // They don't need to be substituted, they're already
// annotated with @Pattern
final class AnnotationCommandInfo implements CommandInfo {

    private static final @NotNull Pattern COMPILED_NAME_PATTERN = Pattern.compile(NAME_PATTERN);
    private static final @NotNull Pattern COMPILED_DESCRIPTION_PATTERN =
            Pattern.compile(DESCRIPTION_PATTERN);

    private static final @NotNull AnnotationElement NAME = AnnotationElement.value(Command.class);
    private static final @NotNull AnnotationElement ALIASES = AnnotationElement.value(Aliases.class);
    private static final @NotNull AnnotationElement DESCRIPTION = AnnotationElement.value(Description.class);

    @org.intellij.lang.annotations.Pattern(NAME_PATTERN)
    private final @NotNull String name;
    private final @NotNull List</*@org.intellij.lang.annotations.Pattern(NAME_PATTERN)*/ @NotNull String> aliases;
    private final @NotNull List</*@org.intellij.lang.annotations.Pattern(NAME_PATTERN)*/ @NotNull String> labels;
    @org.intellij.lang.annotations.Pattern(DESCRIPTION_PATTERN)
    private final @NotNull String description;

    AnnotationCommandInfo(final @NotNull AnnotationValues annotations) {

        Objects.requireNonNull(annotations, "annotations cannot be null");

        this.name = annotations.getString(NAME)
                .orElseThrow(() -> new IllegalArgumentException("Command must be annotated with @Command"));
        this.aliases = annotations.getStrings(ALIASES);
        this.description = annotations.getString(DESCRIPTION).orElse("");

        validateName(name);
        aliases.forEach(AnnotationCommandInfo::validateName);
        validateDescription(description);

        final List<String> labels = new ArrayList<>(aliases.size() + 1); // +1 for name
        labels.add(name);
        labels.addAll(aliases);

        this.labels = labels;
    }

    private static void validateName(final @NotNull String name) {

        if (!COMPILED_NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("Command name must match the pattern " + NAME_PATTERN);
        }
    }

    private static void validateDescription(final @NotNull String description) {

        if (!COMPILED_DESCRIPTION_PATTERN.matcher(description).matches()) {
            throw new IllegalArgumentException("Command description must match the pattern " + DESCRIPTION_PATTERN);
        }
    }

    @org.intellij.lang.annotations.Pattern(NAME_PATTERN)
    @Override
    public @NotNull String getName() {

        return name;
    }

    @Override
    public @Unmodifiable @NotNull List<@NotNull String> getAliases() {

        return aliases;
    }

    @Override
    public @Unmodifiable @NotNull List<@NotNull String> getLabels() {

        return labels;
    }

    @org.intellij.lang.annotations.Pattern(DESCRIPTION_PATTERN)
    @Override
    public @NotNull String getDescription() {

        return description;
    }
}
