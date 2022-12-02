package net.jailgens.enchanted;

import net.jailgens.mirror.InvocationException;
import net.jailgens.mirror.Method;
import net.jailgens.mirror.Parameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
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
                     final @NotNull ConverterRegistry converterRegistry) {

        Objects.requireNonNull(command, "command cannot be null");
        Objects.requireNonNull(method, "method cannot be null");
        Objects.requireNonNull(converterRegistry, "converterRegistry cannot be null");

        if (method.getRawType() != void.class) {
            throw new IllegalArgumentException("method must return void");
        }

        this.command = command;
        this.usage = method.getAnnotations().getString(USAGE).orElse("");
        this.method = (Method<T, Void>) method;
        this.commandParameters = method.getParameters()
                .subList(1, method.getParameters().size())
                .stream()
                .map((parameter) -> (MethodParameter<?>) MethodParameter.of(converterRegistry, (Parameter) parameter))
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
                                          final @NotNull List<@NotNull String> arguments) {

        Objects.requireNonNull(sender, "sender cannot be null");
        Objects.requireNonNull(arguments, "arguments cannot be null");

        final boolean hasExecutor = executorType != null;

        if (hasExecutor && !executorType.isInstance(sender)) {
            return CommandResult.error("You must be a " + executorType.getSimpleName() + " to execute this command");
        }

        final Object[] methodArguments = new Object[commandParameters.size() + (hasExecutor ? 1 : 0)];

        if (executorType != null) {
            assert commandParameters.size() == 0;
            methodArguments[0] = sender;
        }

        final List<String> unusedArguments = new ArrayList<>(arguments);

        for (int i = 0; i < commandParameters.size(); i++) {
            final CommandParameter<?> parameter = commandParameters.get(i);

            if (i >= arguments.size()) {
                if (parameter.isOptional()) {
                    methodArguments[i + 1] = null;
                    continue;
                } else {
                    return CommandResult.error("Missing required argument " + parameter.getName());
                }
            }

            final String argument;

            if (parameter.getDelimiter().isPresent()) {
                final String delimiter = parameter.getDelimiter().get();
                argument = String.join(delimiter, arguments.subList(i, arguments.size()));
                for (int j = i; j < arguments.size(); j++) {
                    unusedArguments.set(j, null);
                }
            } else {
                argument = arguments.get(i);
                unusedArguments.set(i, null);
            }

            final Optional<?> converted = parameter.getConverter().convert(argument);
            if (converted.isEmpty()) {
                return CommandResult.error("Invalid argument " + parameter.getName());
            }

            methodArguments[i + 1] = converted.get();
        }


        if (!Collections.nCopies(unusedArguments.size(), null).equals(unusedArguments)) {
            return CommandResult.error("Invalid usage");
        }

        try {
            method.invoke(command, methodArguments);
            return CommandResult.success();
        } catch (final InvocationException e) {
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
}
