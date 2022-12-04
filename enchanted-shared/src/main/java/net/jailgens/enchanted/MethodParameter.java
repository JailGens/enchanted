package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Join;
import net.jailgens.enchanted.annotations.Param;
import net.jailgens.enchanted.parser.CommandParameterParser;
import net.jailgens.enchanted.resolver.AnnotationCommandParameterResolver;
import net.jailgens.enchanted.resolver.CommandParameterResolver;
import net.jailgens.mirror.AnnotationElement;
import net.jailgens.mirror.AnnotationValues;
import net.jailgens.mirror.Parameter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * A {@link CommandParameter} that is represented by a method {@link Parameter}.
 *
 * @author Sparky983
 * @param <T> the type of the parameter.
 * @since 0.0
 */
@SuppressWarnings("PatternValidation")
final class MethodParameter<T extends @NotNull Object> implements CommandParameter<@NotNull T> {

    private static final @NotNull Pattern COMPILED_NAME_PATTERN = Pattern.compile(NAME_PATTERN);

    private static final @NotNull Class<? extends @NotNull Annotation> OPTIONAL =
            net.jailgens.enchanted.annotations.Optional.class;
    private static final @NotNull AnnotationElement SEPARATOR = AnnotationElement.value(Join.class);
    private static final @NotNull AnnotationElement PARAM = AnnotationElement.value(Param.class);

    private final @NotNull Parameter<@NotNull T> parameter;
    private final @NotNull String name;
    private final @NotNull AnnotationValues annotations;
    private final @NotNull Converter<@NotNull T> converter;
    private final @NotNull CommandParameterParser parser;
    private final @NotNull CommandParameterResolver resolver;

    @Contract(pure = true)
    MethodParameter(final @NotNull Parameter<@NotNull T> parameter,
                    final @NotNull String name,
                    final @NotNull AnnotationValues annotations,
                    final @NotNull Converter<@NotNull T> converter,
                    final @NotNull CommandParameterParser parser,
                    final @NotNull CommandParameterResolver resolver) {

        Objects.requireNonNull(parameter, "parameter cannot be null");
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(annotations, "annotations cannot be null");
        Objects.requireNonNull(converter, "converter cannot be null");
        Objects.requireNonNull(parser, "parser cannot be null");

        if (!COMPILED_NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("Parameter name must match the pattern /" + NAME_PATTERN + "/");
        }

        this.parameter = parameter;
        this.name = name;
        this.annotations = annotations;
        this.converter = converter;
        this.parser = parser;
        this.resolver = resolver;
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

        final CommandParameterResolver resolver = parameter.getRawAnnotations().stream()
                .flatMap((annotation) ->
                        commandManager.getArgumentResolver(annotation.annotationType())
                                .<CommandParameterResolver>map((argumentResolver) -> new AnnotationCommandParameterResolver(annotation, argumentResolver))
                                .stream())
                .reduce((__, ____) -> {
                    // if the accumulator is called we know there is at least 2 annotations
                    throw new IllegalArgumentException("Command cannot have multiple argument resolvers for the same parameter");
                }).orElse(CommandParameterResolver.DEFAULT);

        return new MethodParameter<>(
                parameter,
                parameter.getAnnotations().getString(PARAM)
                        .orElseGet(() -> parameter.getName().replace(' ', '_')),
                parameter.getAnnotations(),
                commandManager.getConverter(parameter.getRawType())
                        .orElseThrow(() -> new IllegalStateException("Required converter for type " + parameter.getRawType() + "not fond")),
                parser,
                resolver
        );
    }

    @org.intellij.lang.annotations.Pattern(NAME_PATTERN)
    @Override
    public @NotNull String getName() {

        return name;
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
    public @NotNull Optional<@NotNull String> parse(final @NotNull Arguments arguments)
            throws ArgumentParseException {

        Objects.requireNonNull(arguments, "arguments cannot be null");

        return parser.parse(arguments);
    }

    @Override
    public @Nullable T resolve(final @NotNull Arguments arguments)
            throws ArgumentParseException {

        return resolver.resolve(this, parameter.getRawType(), arguments);
    }
}
