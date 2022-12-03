package net.jailgens.enchanted;

import net.jailgens.mirror.InvocationException;
import net.jailgens.mirror.Method;
import net.jailgens.mirror.Parameter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.jailgens.enchanted.ClassCommand.USAGE;

/**
 * A method executable, as defined in {@link CommandFactory#createCommand(Object)} under theMethods
 * heading.
 *
 * @author Sparky983
 * @param <T> the type of the declaring class.
 */
final class MethodExecutable<T extends @NotNull Object> implements Inspectable {

    private final @NotNull String usage;
    private final @NotNull T command;
    /**
     * The executor type.
     * <p>
     * Is {@code null} when no executor is specified.
     */
    private final @Nullable Class<? extends @NotNull CommandExecutor> executorType;
    private final @NotNull List<@NotNull CommandParameter<?>> commandParameters;
    private final @NotNull Method<@NotNull T, @NotNull Void> method;

    @SuppressWarnings({"unchecked", "rawtypes"})
    MethodExecutable(final @NotNull T command,
                     final @NotNull Method<? extends @NotNull T, ? extends @NotNull Object> method,
                     final @NotNull CommandManager commandManager) {

        Objects.requireNonNull(command, "command cannot be null");
        Objects.requireNonNull(method, "method cannot be null");
        Objects.requireNonNull(commandManager, "commandManager cannot be null");

        if (method.getRawType() != void.class) {
            throw new IllegalArgumentException("method must return void");
        }

        this.command = command;
        this.usage = method.getAnnotations().getString(USAGE).orElse("");
        this.method = (Method<T, Void>) method;
        this.commandParameters = method.getParameters()
                .subList(1, method.getParameters().size())
                .stream()
                .map((parameter) -> (MethodParameter<?>) MethodParameter.of(commandManager, (Parameter) parameter))
                .collect(Collectors.toUnmodifiableList());

        if (commandParameters.size() >= 1) {
            final Parameter<?> executorParameter = method.getParameters().get(0);

            if (!CommandExecutor.class.isAssignableFrom(executorParameter.getRawType())) {
                throw new IllegalArgumentException("First parameter must be a sub class of " + CommandExecutor.class.getName());
            }

            this.executorType = (Class<? extends CommandExecutor>) executorParameter.getRawType();
        } else {
            this.executorType = null;
        }
    }

    @Override
    public @NotNull CommandResult execute(final @NotNull CommandExecutor sender,
                                          final @NotNull List<@NotNull String> commandArguments) {

        Objects.requireNonNull(sender, "sender cannot be null");
        Objects.requireNonNull(commandArguments, "arguments cannot be null");

        final boolean hasExecutor = executorType != null;

        if (hasExecutor && !executorType.isInstance(sender)) {
            return CommandResult.error("You must be a " + executorType.getSimpleName() + " to execute this command");
        }

        final Object[] methodArguments = new Object[commandParameters.size() + (hasExecutor ? 1 : 0)];

        if (executorType != null) {
            assert commandParameters.size() == 0;
            methodArguments[0] = sender;
        }

        final Arguments arguments = new ListArguments(commandArguments);

        final List<String> parsedArgumentsList = new LinkedList<>();

        for (final CommandParameter<?> parameter : commandParameters) {
            final Optional<String> argument;
            try {
                argument = parameter.parse(arguments);
            } catch (final ArgumentParseException e) {
                final String message = e.getMessage();
                return message == null ?
                        createGenericError(parameter) :
                        createGenericError(parameter, message);
            }

            if (argument.isEmpty()) {
                return createGenericError(parameter);
            }

            parsedArgumentsList.add(argument.get());
        }

        final Arguments parsedArguments = new ListArguments(parsedArgumentsList);

        for (int i = 0; i < commandParameters.size(); i++) {
            final CommandParameter<?> parameter = commandParameters.get(i);

            final Object resolvedArgument;
            try {
                resolvedArgument = parameter.resolve(parsedArguments);
            } catch (final ArgumentParseException e) {
                final String message = e.getMessage();
                return message == null ?
                        createGenericError(parameter) :
                        createGenericError(parameter, message);
            }

            // +1 for sender argument
            methodArguments[i + 1] = resolvedArgument;
        }

        try {
            method.invoke(command, methodArguments);
            return CommandResult.success();
        } catch (final InvocationException e) {
            e.printStackTrace();
            return CommandResult.error("An internal error occurred while executing the command");
        }
    }

    @Override
    public @NotNull String getUsage() {

        return usage;
    }

    @Override
    public @NotNull @Unmodifiable List<? extends @NotNull CommandParameter<?>> getParameters() {

        return commandParameters;
    }

    @Contract(pure = true)
    private static @NotNull CommandResult createGenericError(
            final @NotNull CommandParameter<?> parameter) {

        assert parameter != null;

        return createGenericError(parameter.getName());
    }

    @Contract(pure = true)
    private static @NotNull CommandResult createGenericError(final @NotNull String parameter) {

        assert parameter != null;

        return CommandResult.error("Error parsing argument \"" + parameter + "\"");
    }

    @Contract(pure = true)
    private static @NotNull CommandResult createGenericError(final @NotNull CommandParameter<?> parameter,
                                                             final @NotNull String message) {

        assert parameter != null;

        return createGenericError(parameter.getName(), message);
    }

    @Contract(pure = true)
    private static @NotNull CommandResult createGenericError(final @NotNull String parameter,
                                                             final @NotNull String message) {

        assert parameter != null;
        assert message != null;

        return CommandResult.error("Error parsing argument \"" + parameter + "\": " + message);
    }
}
