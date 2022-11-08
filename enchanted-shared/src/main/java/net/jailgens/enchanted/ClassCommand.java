package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Command.Default;
import net.jailgens.mirror.Method;
import net.jailgens.mirror.TypeDefinition;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The {@link Command} implementation used by {@link SharedCommandFactory}.
 *
 * @author Sparky983
 */
final class ClassCommand<T extends @NotNull Object> extends AnnotatedCommand {

    private static final @NotNull Class<? extends @NotNull Annotation> DEFAULT = Default.class;

    private final @NotNull T command;
    private final @NotNull Map<@NotNull String, @NotNull Command> subCommands;
    private final @Nullable Command defaultCommand;

    @Contract(pure = true)
    public ClassCommand(final @NotNull T command,
                        final @NotNull TypeDefinition<? extends @NotNull T> type,
                        final @NotNull UsageGenerator usageGenerator,
                        final @NotNull MethodCommandFactory methodFactory) {

        super(type.getAnnotations().getString(COMMAND_NAME)
                        .orElseThrow(() -> new IllegalArgumentException("command must be annotated with @" + COMMAND)),
                type.getAnnotations(),
                usageGenerator);

        Objects.requireNonNull(methodFactory, "methodFactory cannot be null");

        this.command = command;

        this.subCommands = findSubCommands(type, methodFactory);

        final Method<? extends T, ?> defaultCommandMethod = findDefaultCommand(type);
        if (defaultCommandMethod == null) {
            this.defaultCommand = null;
        } else {
            this.defaultCommand = methodFactory.createCommand(command, defaultCommandMethod);
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

    @Override
    public @NotNull CommandResult execute(final @NotNull CommandExecutor sender, final @NotNull List<@NotNull String> arguments) {

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
