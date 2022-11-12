package net.jailgens.enchanted.converter;

import net.jailgens.enchanted.Converter;
import net.jailgens.enchanted.ConverterRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class SharedConverterRegistry implements ConverterRegistry {

    private final Map<Class<?>, Converter<?>> converters = new HashMap<>();

    public SharedConverterRegistry() {

        registerConverter(String.class, new StringConverter());
        registerConverter(Integer.class, new IntegerConverter());
        registerConverter(int.class, new IntegerConverter());
        registerConverter(Double.class, new DoubleConverter());
        registerConverter(double.class, new DoubleConverter());
        registerConverter(Float.class, new FloatConverter());
        registerConverter(float.class, new FloatConverter());
        registerConverter(Boolean.class, new BooleanConverter());
        registerConverter(boolean.class, new BooleanConverter());
        registerConverter(Long.class, new LongConverter());
        registerConverter(long.class, new LongConverter());
        registerConverter(Short.class, new ShortConverter());
        registerConverter(short.class, new ShortConverter());
        registerConverter(Byte.class, new ByteConverter());
        registerConverter(byte.class, new ByteConverter());
        registerConverter(Character.class, new CharacterConverter());
        registerConverter(char.class, new CharacterConverter());
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
    public <T> Optional<@NotNull Converter<@NotNull T>> getConverter(final @NotNull Class<@NotNull T> type) {

        Objects.requireNonNull(type, "type cannot be null");

        return Optional.ofNullable((Converter<T>) converters.get(type));
    }

    @Override
    public boolean hasConverter(final @NotNull Class<?> type) {

        Objects.requireNonNull(type, "type cannot be null");

        return converters.containsKey(type);
    }
}
