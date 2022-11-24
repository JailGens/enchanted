package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Command.Default;
import net.jailgens.enchanted.annotations.Usage;
import net.jailgens.mirror.AnnotationElement;
import net.jailgens.mirror.Method;
import net.jailgens.mirror.TypeDefinition;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The {@link Command} implementation used by {@link SharedCommandFactory} as defined by
 * {@link CommandFactory#createCommand(Object)}.
 *
 * @author Sparky983
 */
@SuppressWarnings("PatternValidation") // for @Subst annotations
// The strings are already annotated with @Pattern, no need to substitute them
final class ClassCommand implements Command {

    static final @NotNull AnnotationElement USAGE = AnnotationElement.value(Usage.class);

    private static final @NotNull Class<? extends @NotNull Annotation> DEFAULT = Default.class;
    private static final @NotNull Class<? extends @NotNull Annotation> COMMAND = net.jailgens.enchanted.annotations.Command.class;

    private final @NotNull CommandMap subCommands = CommandMap.create();
    private final @Nullable Executable defaultCommand;

    private final @NotNull CommandInfo commandInfo;
    private final @NotNull String usage;

    @Contract(pure = true)
    <T extends @NotNull Object> ClassCommand(final @NotNull T command,
                                             final @NotNull TypeDefinition<? extends @NotNull T> type,
                                             final @NotNull CommandInfo commandInfo,
                                             final @NotNull ConverterRegistry converterRegistry) {

        Objects.requireNonNull(command, "command cannot be null");
        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(commandInfo, "commandInfo cannot be null");
        Objects.requireNonNull(converterRegistry, "converterRegistry cannot be null");

        type.getMethods().stream()
                .filter((method) -> method.getAnnotations().hasAnnotation(COMMAND))
                .map((method) -> new SharedCommand(
                        new MethodExecutable<>(command, method, converterRegistry),
                        new AnnotationCommandInfo(method.getAnnotations())
                ))
                .forEach(subCommands::registerCommand);

        final Method<? extends T, ?> defaultCommandMethod = findDefaultCommand(type);

        if (defaultCommandMethod == null) {
            this.defaultCommand = null;
        } else {
            this.defaultCommand = new MethodExecutable<>(command, defaultCommandMethod, converterRegistry);
        }

        this.commandInfo = commandInfo;
        this.usage = type.getAnnotations()
                .getString(USAGE)
                .orElse("");
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

    @Pattern(NAME_PATTERN)
    @Override
    public @NotNull String getName() {

        return commandInfo.getName();
    }

    @Override
    public @Unmodifiable @NotNull List<String> getAliases() {

        return commandInfo.getAliases();
    }

    @Override
    public @Unmodifiable @NotNull List<String> getLabels() {

        return commandInfo.getLabels();
    }

    @Override
    public @NotNull String getUsage() {

        return usage;
    }

    @Pattern(DESCRIPTION_PATTERN)
    @Override
    public @NotNull String getDescription(final @NotNull Locale locale) {

        Objects.requireNonNull(locale, "locale cannot be null");

        return getDescription();
    }

    @Pattern(DESCRIPTION_PATTERN)
    @Override
    public @NotNull String getDescription() {

        return commandInfo.getDescription();
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
            command = subCommands.getCommand(arguments.get(0)).orElse(null);
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
