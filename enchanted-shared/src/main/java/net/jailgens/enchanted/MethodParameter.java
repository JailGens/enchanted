package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Join;
import net.jailgens.mirror.AnnotationElement;
import net.jailgens.mirror.AnnotationValues;
import net.jailgens.mirror.Parameter;
import org.intellij.lang.annotations.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link CommandParameter} that is represented by a method {@link Parameter}.
 *
 * @author Sparky983
 * @param <T> the type of the parameter.
 * @since 0.0
 */
final class MethodParameter<T extends @NotNull Object> implements CommandParameter<@NotNull T> {

    private static final @NotNull Class<? extends @NotNull Annotation> OPTIONAL =
            net.jailgens.enchanted.annotations.Optional.class;
    private static final @NotNull AnnotationElement SEPARATOR =
            AnnotationElement.value(Join.class);

    private final @NotNull Parameter<@NotNull T> parameter;
    private final @NotNull AnnotationValues annotations;
    private final @NotNull Converter<@NotNull T> converter;
    private final @NotNull CommandParameterParser parser;

    @Contract(pure = true)
    MethodParameter(final @NotNull Parameter<@NotNull T> parameter,
                    final @NotNull AnnotationValues annotations,
                    final @NotNull Converter<@NotNull T> converter,
                    final @NotNull CommandParameterParser parser) {

        Objects.requireNonNull(parameter, "parameter cannot be null");
        Objects.requireNonNull(annotations, "annotations cannot be null");
        Objects.requireNonNull(converter, "converter cannot be null");
        Objects.requireNonNull(parser, "parser cannot be null");

        this.parameter = parameter;
        this.annotations = annotations;
        this.converter = converter;
        this.parser = parser;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static <T extends @NotNull Object> @NotNull MethodParameter<@NotNull T> of(
            final @NotNull CommandManager commandManager,
            final @NotNull Parameter<@NotNull T> parameter) {

        Objects.requireNonNull(parameter, "parameter cannot be null");

        // TODO(Sparky983): This generic stuff is a mess

        final CommandParameterParser parser = parameter.getRawAnnotations().stream()
                .flatMap((annotation) ->
                        commandManager.getArgumentParser(annotation.annotationType())
                                .<CommandParameterParser>map((argumentParser) -> (arguments) -> (Optional<String>) ((ArgumentParser) argumentParser).parse(annotation, arguments))
                                .stream())
                .reduce((__, ____) -> {
                    // if the accumulator is called we know there is at least 2 annotations
                    throw new IllegalArgumentException("Command cannot have multiple argument parsers for the same parameter");
                })
                .orElse(CommandParameterParser.DEFAULT);

        return new MethodParameter<>(
                parameter,
                parameter.getAnnotations(),
                commandManager.getConverter(parameter.getRawType())
                        .orElseThrow(() -> new IllegalStateException("Required converter for type " + parameter.getRawType() + "not fond")),
                parser);
    }

    @SuppressWarnings("PatternValidation")
    @Identifier
    @Override
    public @NotNull String getName() {

        return parameter.getName().replace(' ', '_');
    }

    @Override
    public boolean isOptional() {

        return annotations.hasAnnotation(OPTIONAL);
    }

    @Override
    public @NotNull Optional<@NotNull String> getDelimiter() {

        return annotations.getString(SEPARATOR);
    }

    @Override
    public @NotNull Converter<@NotNull T> getConverter() {

        return converter;
    }

    @Override
    public @NotNull Optional<@NotNull String> parse(final @NotNull Arguments arguments) {

        Objects.requireNonNull(arguments, "arguments cannot be null");

        return parser.parse(arguments);
    }
}
