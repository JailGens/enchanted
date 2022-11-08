package net.jailgens.enchanted;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class SharedConverterRegistryTest {

    // TODO(Sparky983): Fix the test names, they are not descriptive enough because I used copilot
    //  for most of them.

    ConverterRegistry converterRegistry;

    @BeforeEach
    void setUp() {

        converterRegistry = new SharedConverterRegistry();
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullType_When_RegisterConverter_Then_Throws() {

        final Converter<?> converter = mock(Converter.class);

        assertThrows(NullPointerException.class, () -> converterRegistry.registerConverter(null, converter));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullConverter_When_RegisterConverter_Then_Throws() {

        assertThrows(NullPointerException.class, () -> converterRegistry.registerConverter(String.class, null));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullType_When_GetConverter_Then_Throws() {

        assertThrows(NullPointerException.class, () -> converterRegistry.getConverter(null));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullType_When_HasConverter_Then_Throws() {

        assertThrows(NullPointerException.class, () -> converterRegistry.hasConverter(null));
    }

    @Test
    void Given_ConverterRegistry_When_GetConverter_Then_ReturnsEmptyOptional() {

        final Optional<Converter<Object>> converter = converterRegistry.getConverter(Object.class);

        assertTrue(converter.isEmpty());
    }

    @Test
    void Given_ConverterRegistry_When_HasConverter_Then_ReturnsFalse() {

        final boolean hasConverter = converterRegistry.hasConverter(Object.class);

        assertFalse(hasConverter);
    }

    @SuppressWarnings("unchecked")
    @Test
    void Given_ConverterRegistryWithConverter_When_GetConverter_Then_ReturnsConverter() {

        final Converter<Object> converter = mock(Converter.class);
        converterRegistry.registerConverter(Object.class, converter);

        final Optional<Converter<Object>> converterOptional = converterRegistry.getConverter(Object.class);

        assertEquals(Optional.of(converter), converterOptional);
    }

    @SuppressWarnings("unchecked")
    @Test
    void Given_ConverterRegistryWithConverter_When_HasConverter_Then_ReturnsTrue() {

        final Converter<Object> converter = mock(Converter.class);

        converterRegistry.registerConverter(Object.class, converter);

        final boolean hasConverter = converterRegistry.hasConverter(Object.class);

        assertTrue(hasConverter);
    }

    @SuppressWarnings("unchecked")
    @Test
    void Given_ConverterRegistryWithConverter_When_ReregisterConverter_Then_GetConverterReturnsNewConverter() {

        final Converter<Object> converter1 = mock(Converter.class);
        final Converter<Object> converter2 = mock(Converter.class);

        converterRegistry.registerConverter(Object.class, converter1);
        converterRegistry.registerConverter(Object.class, converter2);

        final Optional<Converter<Object>> converter = converterRegistry.getConverter(Object.class);

        assertEquals(Optional.of(converter2), converter);
    }

    @Test
    void Given_ConverterRegistry_When_GetStringConverter_Then_ConverterReturnsString() {

        final String string = "test";

        final Optional<Converter<String>> converter = converterRegistry.getConverter(String.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of("test"), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetIntConverter_Then_ConverterReturnsInt() {

        final String string = "1";

        final Optional<Converter<Integer>> converter = converterRegistry.getConverter(Integer.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(1), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetIntConverter_ThenConverterReturnsNegativeInt() {

        final String string = "-1";

        final Optional<Converter<Integer>> converter = converterRegistry.getConverter(Integer.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(-1), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetIntConverter_ThenConverterReturnsZero() {

        final String string = "0";

        final Optional<Converter<Integer>> converter = converterRegistry.getConverter(Integer.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(0), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetIntConverter_ThenConverterReturnsEmptyOptional() {

        final String string = "test";

        final Optional<Converter<Integer>> converter = converterRegistry.getConverter(Integer.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.empty(), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetIntConverter_ThenConverterReturnsEmptyOptionalOnOverflow() {

        final String string = Integer.MAX_VALUE + "0";

        final Optional<Converter<Integer>> converter = converterRegistry.getConverter(Integer.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.empty(), converter.get().convert(string));
    }

    @SuppressWarnings("MagicNumber")
    @Test
    void Given_ConverterRegistry_When_GetDoubleConverter_Then_ConverterReturnsDouble() {

        final String string = "1.1";

        final Optional<Converter<Double>> converter = converterRegistry.getConverter(Double.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(1.1), converter.get().convert(string));
    }

    @SuppressWarnings("MagicNumber")
    @Test
    void Given_ConverterRegistry_When_GetDoubleConverter_ThenConverterReturnsNegativeDouble() {

        final String string = "-1.1";

        final Optional<Converter<Double>> converter = converterRegistry.getConverter(Double.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(-1.1), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetDoubleConverter_ThenConverterReturnsZero() {

        final String string = "0.0";

        final Optional<Converter<Double>> converter = converterRegistry.getConverter(Double.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(0.0), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetDoubleConverter_ThenConverterReturnsInteger() {

        final String string = "1";

        final Optional<Converter<Double>> converter = converterRegistry.getConverter(Double.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(1.0), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetDoubleConverter_ThenConverterReturnsEmptyOptional() {

        final String string = "test";

        final Optional<Converter<Double>> converter = converterRegistry.getConverter(Double.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.empty(), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetFloatConverter_Then_ConverterReturnsFloat() {

        final String string = "1.0";

        final Optional<Converter<Float>> converter = converterRegistry.getConverter(Float.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(1.0f), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetFloatConverter_ThenConverterReturnsNegativeFloat() {

        final String string = "-1.0";

        final Optional<Converter<Float>> converter = converterRegistry.getConverter(Float.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(-1.0f), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetFloatConverter_ThenConverterReturnsZero() {

        final String string = "0.0";

        final Optional<Converter<Float>> converter = converterRegistry.getConverter(Float.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(0.0f), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetFloatConverter_ThenConverterReturnsInteger() {

        final String string = "1";

        final Optional<Converter<Float>> converter = converterRegistry.getConverter(Float.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(1.0f), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetFloatConverter_ThenConverterReturnsEmptyOptional() {

        final String string = "test";

        final Optional<Converter<Float>> converter = converterRegistry.getConverter(Float.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.empty(), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetLongConverter_Then_ConverterReturnsLong() {

        final String string = "1";

        final Optional<Converter<Long>> converter = converterRegistry.getConverter(Long.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(1L), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetLongConverter_ThenConverterReturnsNegativeLong() {

        final String string = "-1";

        final Optional<Converter<Long>> converter = converterRegistry.getConverter(Long.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(-1L), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetLongConverter_ThenConverterReturnsZero() {

        final String string = "0";

        final Optional<Converter<Long>> converter = converterRegistry.getConverter(Long.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(0L), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetLongConverter_ThenConverterReturnsEmptyOptional() {

        final String string = "test";

        final Optional<Converter<Long>> converter = converterRegistry.getConverter(Long.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.empty(), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetLongConverter_Then_ConverterReturnsEmptyOptionalOnOverflow() {

        final String string = Long.MAX_VALUE + "0";

        final Optional<Converter<Long>> converter = converterRegistry.getConverter(Long.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.empty(), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetBooleanConverter_Then_ConverterReturnsTrue() {

        final String string = "true";

        final Optional<Converter<Boolean>> converter = converterRegistry.getConverter(Boolean.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(true), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetBooleanConverter_Then_ConverterReturnsFalse() {

        final String string = "false";

        final Optional<Converter<Boolean>> converter = converterRegistry.getConverter(Boolean.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of(false), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetBooleanConverter_Then_ConverterReturnsEmptyOptional() {

        final String string = "test";

        final Optional<Converter<Boolean>> converter = converterRegistry.getConverter(Boolean.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.empty(), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetCharConverter_Then_ConverterReturnsChar() {

        final String string = "a";

        final Optional<Converter<Character>> converter = converterRegistry.getConverter(Character.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of('a'), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetCharConverter_Then_ConverterReturnsEmptyOptional() {

        final String string = "test";

        final Optional<Converter<Character>> converter = converterRegistry.getConverter(Character.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.empty(), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetByteConverter_Then_ConverterReturnsByte() {

        final String string = "1";

        final Optional<Converter<Byte>> converter = converterRegistry.getConverter(Byte.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of((byte) 1), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetByteConverter_Then_ConverterReturnsNegativeByte() {

        final String string = "-1";

        final Optional<Converter<Byte>> converter = converterRegistry.getConverter(Byte.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of((byte) -1), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetByteConverter_Then_ConverterReturnsZero() {

        final String string = "0";

        final Optional<Converter<Byte>> converter = converterRegistry.getConverter(Byte.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of((byte) 0), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetByteConverter_Then_ConverterReturnsEmptyOptional() {

        final String string = "test";

        final Optional<Converter<Byte>> converter = converterRegistry.getConverter(Byte.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.empty(), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetShortConverter_Then_ConverterReturnsEmptyOptionalOnOverflow() {

        final String string = String.valueOf(Short.MAX_VALUE) + "0";

        final Optional<Converter<Short>> converter = converterRegistry.getConverter(Short.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.empty(), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetShortConverter_Then_ConverterReturnsShort() {

        final String string = "1";

        final Optional<Converter<Short>> converter = converterRegistry.getConverter(Short.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of((short) 1), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetShortConverter_Then_ConverterReturnsNegativeShort() {

        final String string = "-1";

        final Optional<Converter<Short>> converter = converterRegistry.getConverter(Short.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of((short) -1), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetShortConverter_Then_ConverterReturnsZero() {

        final String string = "0";

        final Optional<Converter<Short>> converter = converterRegistry.getConverter(Short.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.of((short) 0), converter.get().convert(string));
    }

    @Test
    void Given_ConverterRegistry_When_GetShortConverter_Then_ConverterReturnsEmptyOptional() {

        final String string = "test";

        final Optional<Converter<Short>> converter = converterRegistry.getConverter(Short.class);

        assertTrue(converter.isPresent());
        assertEquals(Optional.empty(), converter.get().convert(string));
    }
}
