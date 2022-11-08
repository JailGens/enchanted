package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Aliases;
import net.jailgens.enchanted.annotations.Description;
import net.jailgens.enchanted.annotations.Usage;
import net.jailgens.mirror.AnnotationElement;
import net.jailgens.mirror.AnnotationValues;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

abstract class AnnotatedCommand implements Command {

    private static final @NotNull Pattern COMPILED_NAME_PATTERN = Pattern.compile(NAME_PATTERN);
    private static final @NotNull Pattern COMPILED_DESCRIPTION_PATTERN = Pattern.compile(DESCRIPTION_PATTERN);

    static final @NotNull String COMMAND = net.jailgens.enchanted.annotations.Command.class.getName();
    static final @NotNull AnnotationElement COMMAND_NAME = AnnotationElement.value(COMMAND);
    private static final @NotNull AnnotationElement ALIASES = AnnotationElement.value(Aliases.class);
    private static final @NotNull AnnotationElement DESCRIPTION = AnnotationElement.value(Description.class);
    private static final @NotNull AnnotationElement USAGE = AnnotationElement.value(Usage.class);

    @Subst("name")
    private final @NotNull String name;
    private final @NotNull @Unmodifiable List<String> aliases;
    private final @NotNull @Unmodifiable List<String> labels;
    private final @NotNull String usage;
    private final @NotNull String description;

    AnnotatedCommand(final @NotNull String name,
                     final @NotNull AnnotationValues annotations,
                     final @NotNull UsageGenerator usageGenerator) {

        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(annotations, "annotations cannot be null");
        Objects.requireNonNull(usageGenerator, "usageGenerator cannot be null");

        if (!COMPILED_NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("command name must match the pattern /" + NAME_PATTERN + "/");
        }

        this.name = name;
        this.aliases = annotations.getStrings(ALIASES);

        for (final String alias : aliases) {
            if (!COMPILED_NAME_PATTERN.matcher(alias).matches()) {
                throw new IllegalArgumentException("command alias must match the pattern /" + NAME_PATTERN + "/");
            }
        }

        if (aliases.contains(name)) {
            throw new IllegalArgumentException("command name cannot be an alias");
        }

        this.labels = createLabelsList(name, aliases);
        this.usage = annotations.getString(USAGE).orElse(usageGenerator.generateUsage(this));
        this.description = annotations.getString(DESCRIPTION).orElse("");

        if (!COMPILED_DESCRIPTION_PATTERN.matcher(description).matches()) {
            throw new IllegalArgumentException("command description must match the pattern /" + DESCRIPTION_PATTERN + "/");
        }
    }

    /**
     * A helper method to create the list of labels for this command.
     *
     * @param name the name.
     * @param aliases the aliases.
     * @return the list of labels.
     */
    private static @NotNull @Unmodifiable List<@NotNull String> createLabelsList(
            final @NotNull String name,
            final @NotNull List<@NotNull String> aliases) {

        final List<String> labels = new ArrayList<>(aliases.size() + 1);
        labels.add(name);
        labels.addAll(aliases);
        return List.copyOf(labels);
    }

    @org.intellij.lang.annotations.Pattern(NAME_PATTERN)
    @Override
    public @NotNull String getName() {

        return name;
    }

    @Override
    public @Unmodifiable @NotNull List<String> getAliases() {

        return aliases;
    }

    @Override
    public @Unmodifiable @NotNull List<String> getLabels() {

        return labels;
    }

    @Override
    public @NotNull String getUsage() {

        return usage;
    }

    @Override
    public @NotNull String getDescription(final @NotNull Locale locale) {

        Objects.requireNonNull(locale, "locale cannot be null");

        return description;
    }

    @Override
    public @NotNull String getDescription() {

        return description;
    }
}
