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
final class MethodParameter<T extends @NotNull Object> implements CommandParameter {

    private static final @NotNull Class<? extends @NotNull Annotation> OPTIONAL =
            net.jailgens.enchanted.annotations.Optional.class;
    private static final @NotNull AnnotationElement SEPARATOR =
            AnnotationElement.value(Join.class);

    private final @NotNull Parameter<@NotNull T> parameter;
    private final @NotNull AnnotationValues annotations;
    private final @NotNull Converter<@NotNull T> converter;

    @Contract(pure = true)
    MethodParameter(final @NotNull Parameter<@NotNull T> parameter,
                    final @NotNull AnnotationValues annotations,
                    final @NotNull Converter<@NotNull T> converter) {

        Objects.requireNonNull(parameter, "parameter cannot be null");
        Objects.requireNonNull(annotations, "annotations cannot be null");
        Objects.requireNonNull(converter, "converter cannot be null");

        this.parameter = parameter;
        this.annotations = annotations;
        this.converter = converter;
    }

    static <T extends @NotNull Object> @NotNull MethodParameter<@NotNull T> of(
            final @NotNull ConverterRegistry converterRegistry,
            final @NotNull Parameter<@NotNull T> parameter) {

        Objects.requireNonNull(parameter, "parameter cannot be null");

        return new MethodParameter<>(
                parameter,
                parameter.getAnnotations(),
                converterRegistry.getConverter(parameter.getRawType())
                        .orElseThrow(() -> new IllegalStateException("Required converter for type " + parameter.getRawType() + "not fond"))
        );
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

    @Contract(pure = true)
    public @NotNull Converter<@NotNull T> getConverter() {

        return converter;
    }
}
