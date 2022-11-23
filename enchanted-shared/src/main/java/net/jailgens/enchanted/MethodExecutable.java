package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Join;
import net.jailgens.mirror.AnnotationElement;
import net.jailgens.mirror.AnnotationValues;
import net.jailgens.mirror.InvocationException;
import net.jailgens.mirror.Method;
import net.jailgens.mirror.Parameter;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.jailgens.enchanted.ClassCommand.USAGE;

final class MethodExecutable<T> implements Executable {

    private static final @NotNull Class<? extends @NotNull Annotation> OPTIONAL =
            net.jailgens.enchanted.annotations.Optional.class;
    private static final @NotNull Class<? extends @NotNull Annotation> JOIN = Join.class;
    private static final @NotNull AnnotationElement DELIMITER = AnnotationElement.value(JOIN);

    private final @NotNull String usage;

    private final @NotNull T command;
    private final @NotNull Class<? extends @NotNull CommandExecutor> executorType;
    private final @NotNull List<@NotNull Parameter<? extends @NotNull Object>> methodParameters;
    private final @NotNull List<@NotNull Parameter<? extends @NotNull Object>> commandParameters;
    private final @NotNull List<@NotNull Converter<? extends @NotNull Object>> converters;
    private final @NotNull Method<@NotNull T, @NotNull Void> method;

    @SuppressWarnings("unchecked")
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
        this.methodParameters = method.getParameters();

        if (methodParameters.size() < 1) {
            this.commandParameters = List.of();
        } else {
            this.commandParameters = methodParameters.subList(1, methodParameters.size());
        }

        if (methodParameters.size() >= 1) {
            final Parameter<?> executorParameter = methodParameters.get(0);

            if (!CommandExecutor.class.isAssignableFrom(executorParameter.getRawType())) {
                throw new IllegalArgumentException("First parameter must be a sub class of " + CommandExecutor.class.getName());
            }

            this.executorType = (Class<? extends CommandExecutor>) executorParameter.getRawType();
        } else {
            this.executorType = CommandExecutor.class;
        }

        this.converters = commandParameters.stream()
                .map((parameter) -> converterRegistry.getConverter(parameter.getRawType())
                        .orElseThrow(() -> new IllegalStateException("No converter for type " + parameter.getRawType().getName())))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public @NotNull CommandResult execute(final @NotNull CommandExecutor sender,
                                          final @NotNull List<@NotNull String> arguments) {

        Objects.requireNonNull(sender, "sender cannot be null");
        Objects.requireNonNull(arguments, "arguments cannot be null");

        if (!executorType.isInstance(sender)) {
            return CommandResult.error("You must be a " + executorType.getSimpleName() + " to execute this command");
        }

        final Object[] methodArguments = new Object[methodParameters.size()];

        if (methodParameters.size() > 0) {
            methodArguments[0] = sender;
        }

        final List<String> unusedArguments = new ArrayList<>(arguments);

        for (int i = 0; i < commandParameters.size(); i++) {
            final Parameter<?> parameter = commandParameters.get(i);
            final AnnotationValues annotations = parameter.getAnnotations();

            if (i >= arguments.size()) {
                if (annotations.hasAnnotation(OPTIONAL)) {
                    methodArguments[i + 1] = null;
                    continue;
                } else {
                    return CommandResult.error("Missing required argument " + parameter.getRawType().getName());
                }
            }

            final String argument;

            if (annotations.hasAnnotation(JOIN)) {
                final String delimiter = annotations.getString(DELIMITER).orElse(" ");
                argument = String.join(delimiter, arguments.subList(i, arguments.size()));
                for (int j = i; j < arguments.size(); j++) {
                    unusedArguments.set(j, null);
                }
            } else {
                argument = arguments.get(i);
                unusedArguments.set(i, null);
            }

            final Optional<?> converted = converters.get(i).convert(argument);
            if (converted.isEmpty()) {
                return CommandResult.error("Invalid argument for type " + parameter.getRawType());
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
}
