package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Command.Default;
import net.jailgens.mirror.Method;
import net.jailgens.mirror.TypeDefinition;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The {@link Command} implementation used by {@link SharedCommandFactory}.
 *
 * @author Sparky983
 */
final class ClassCommand<T extends @NotNull Object> implements Command {

    private static final @NotNull Pattern COMPILED_NAME_PATTERN = Pattern.compile(NAME_PATTERN);
    private static final @NotNull Pattern COMPILED_DESCRIPTION_PATTERN = Pattern.compile(DESCRIPTION_PATTERN);

    private static final @NotNull Class<? extends @NotNull Annotation> DEFAULT = Default.class;
    private static final @NotNull Class<? extends @NotNull Annotation> COMMAND = net.jailgens.enchanted.annotations.Command.class;

    @Subst("name")
    @org.intellij.lang.annotations.Pattern(NAME_PATTERN)
    private final @NotNull String name;
    private final @NotNull List</* @org.intellij.lang.annotations.Pattern(NAME_PATTERN) */String> aliases;
    private final @NotNull List</* @org.intellij.lang.annotations.Pattern(NAME_PATTERN) */String> labels;
    private final @NotNull String usage;
    @Subst("description")
    @org.intellij.lang.annotations.Pattern(DESCRIPTION_PATTERN)
    private final @NotNull String description;

    private final @NotNull T command;
    private final @NotNull Map<@NotNull String, @NotNull Command> subCommands;
    private final @Nullable Command defaultCommand;

    @Contract(pure = true)
    ClassCommand(final @NotNull T command,
                 final @NotNull TypeDefinition<? extends @NotNull T> type,
                 final @NotNull CommandInfo commandInfo,
                 final @NotNull UsageGenerator usageGenerator,
                 final @NotNull MethodCommandFactory commandFactory) {

        Objects.requireNonNull(command, "command cannot be null");
        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(commandInfo, "commandInfo cannot be null");
        Objects.requireNonNull(commandFactory, "commandFactory cannot be null");

        this.command = command;

        this.subCommands = findSubCommands(type, commandFactory);

        final Method<? extends T, ?> defaultCommandMethod = findDefaultCommand(type);

        if (defaultCommandMethod == null) {
            this.defaultCommand = null;
        } else {
            this.defaultCommand = commandFactory.createCommand(command, defaultCommandMethod);
        }

        @Subst("name") final String name =
                commandInfo.getName().orElseThrow(IllegalArgumentException::new);
        this.name = name;

        if (!COMPILED_NAME_PATTERN.matcher(this.name).matches()) {
            throw new IllegalArgumentException("Command name must match " + NAME_PATTERN);
        }

        this.aliases = commandInfo.getAliases();

        for (final String alias : this.aliases) {
            if (!COMPILED_NAME_PATTERN.matcher(alias).matches()) {
                throw new IllegalArgumentException("Command aliases must match " + NAME_PATTERN);
            }
        }

        if (aliases.contains(name)) {
            throw new IllegalArgumentException("command name cannot be an alias");
        }

        this.labels = createLabelsList(this.name, aliases);
        this.usage = commandInfo.getUsage().orElseGet(() -> usageGenerator.generateUsage(this));

        @Subst("description") final String description = commandInfo.getDescription().orElse("");
        this.description = description;

        if (!COMPILED_DESCRIPTION_PATTERN.matcher(this.description).matches()) {
            throw new IllegalArgumentException("Command description must match " + DESCRIPTION_PATTERN);
        }
    }

    /**
     * A helper method to find all sub commands.
     *
     * @param type the type.
     * @return a map of sub command labels to sub command.
     * @throws IllegalArgumentException if a sub command failed validation.
     */
    private @NotNull Map<@NotNull String, @NotNull  Command> findSubCommands(
            final @NotNull TypeDefinition<? extends @NotNull T> type,
            final @NotNull MethodCommandFactory factory) {

        final List<Command> commands = type.getMethods().stream()
                .filter((method) -> method.getAnnotations().hasAnnotation(COMMAND))
                .map((method) -> factory.createCommand(command, method))
                .collect(Collectors.toUnmodifiableList());

        final Map<String, Command> commandsMap = new HashMap<>();

        for (final Command command : commands) {
            for (final String label : command.getLabels()) {
                if (commandsMap.containsKey(label)) {
                    throw new IllegalArgumentException("Multiple sub commands with label \"" + label + "\"");
                }
                commandsMap.put(label, command);
            }
        }

        return commandsMap;
    }

    /**
     * A helper method to find the default command for this command.
     *
     * @param type the type.
     * @throws IllegalArgumentException if there are multiple default commands.
     */
    private static <T extends @NotNull Object> @Nullable Method<
            ? extends @NotNull T,
            ? extends @NotNull Object
            > findDefaultCommand(final @NotNull TypeDefinition<? extends @NotNull T> type) {

        final List<Method<? extends T, ?>> methods = type.getMethods().stream()
                .filter((method) -> method.getAnnotations().hasAnnotation(DEFAULT))
                .collect(Collectors.toList());

        if (methods.size() > 1) {
            throw new IllegalArgumentException("command cannot have more than one default command");
        }

        return methods.isEmpty() ? null : methods.get(0);
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

    @org.intellij.lang.annotations.Pattern(DESCRIPTION_PATTERN)
    @Override
    public @NotNull String getDescription(final @NotNull Locale locale) {

        Objects.requireNonNull(locale, "locale cannot be null");

        return description;
    }

    @org.intellij.lang.annotations.Pattern(DESCRIPTION_PATTERN)
    @Override
    public @NotNull String getDescription() {

        return description;
    }

    @Override
    public @NotNull CommandResult execute(final @NotNull CommandExecutor sender,
                                          final @NotNull List<@NotNull String> arguments) {

        Objects.requireNonNull(sender, "sender cannot be null");
        Objects.requireNonNull(arguments, "arguments cannot be null");

        for (int i = 0; i < arguments.size(); i++) {
            Objects.requireNonNull(arguments.get(i), "arguments[" + i + "] cannot be null");
        }

        final Command command;

        if (arguments.size() > 1) {
            command = subCommands.get(arguments.get(0));
        } else {
            command = null;
        }

        if (command == null) {
            return defaultCommand == null ?
                    CommandResult.error("Invalid usage, try: " + getUsage()) :
                    defaultCommand.execute(sender, arguments);
        }

        return command.execute(sender, arguments.subList(1, arguments.size()));
    }
}
