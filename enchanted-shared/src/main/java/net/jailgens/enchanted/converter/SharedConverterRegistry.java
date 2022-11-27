package net.jailgens.enchanted.converter;

import net.jailgens.enchanted.Converter;
import net.jailgens.enchanted.ConverterRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * The shared implementation of {@link ConverterRegistry}.
 *
 * @author Sparky983
 */
public final class SharedConverterRegistry implements ConverterRegistry {

    private static final @NotNull Converter<@NotNull String> STRING_CONVERTER = new StringConverter();
    private static final @NotNull Converter<@NotNull Integer> INTEGER_CONVERTER = new IntegerConverter();
    private static final @NotNull Converter<@NotNull Double> DOUBLE_CONVERTER = new DoubleConverter();
    private static final @NotNull Converter<@NotNull Float> FLOAT_CONVERTER = new FloatConverter();
    private static final @NotNull Converter<@NotNull Boolean> BOOLEAN_CONVERTER = new BooleanConverter();
    private static final @NotNull Converter<@NotNull Long> LONG_CONVERTER = new LongConverter();
    private static final @NotNull Converter<@NotNull Short> SHORT_CONVERTER = new ShortConverter();
    private static final @NotNull Converter<@NotNull Byte> BYTE_CONVERTER = new ByteConverter();
    private static final @NotNull Converter<@NotNull Character> CHARACTER_CONVERTER = new CharacterConverter();

    private final @NotNull Map<@NotNull Class<? extends @NotNull Object>, Converter<? extends @NotNull Object>> converters = new HashMap<>();

    public SharedConverterRegistry() {

        registerConverter(String.class, STRING_CONVERTER);
        registerConverter(Integer.class, INTEGER_CONVERTER);
        registerConverter(int.class, INTEGER_CONVERTER);
        registerConverter(Double.class, DOUBLE_CONVERTER);
        registerConverter(double.class, DOUBLE_CONVERTER);
        registerConverter(Float.class, FLOAT_CONVERTER);
        registerConverter(float.class, FLOAT_CONVERTER);
        registerConverter(Boolean.class, BOOLEAN_CONVERTER);
        registerConverter(boolean.class, BOOLEAN_CONVERTER);
        registerConverter(Long.class, LONG_CONVERTER);
        registerConverter(long.class, LONG_CONVERTER);
        registerConverter(Short.class, SHORT_CONVERTER);
        registerConverter(short.class, SHORT_CONVERTER);
        registerConverter(Byte.class, BYTE_CONVERTER);
        registerConverter(byte.class, BYTE_CONVERTER);
        registerConverter(Character.class, CHARACTER_CONVERTER);
        registerConverter(char.class, CHARACTER_CONVERTER);
    }

    @Override
    public <T> void registerConverter(final @NotNull Class<@NotNull T> type,
                                      final @NotNull Converter<@NotNull T> converter) {

        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(converter, "converter cannot be null");

        converters.put(type, converter);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Optional<? extends @NotNull Converter<@NotNull T>> getConverter(final @NotNull Class<@NotNull T> type) {

        Objects.requireNonNull(type, "type cannot be null");

        return Optional.ofNullable((Converter<T>) converters.get(type));
    }

    @Override
    public boolean hasConverter(final @NotNull Class<?> type) {

        Objects.requireNonNull(type, "type cannot be null");

        return converters.containsKey(type);
    }
}
