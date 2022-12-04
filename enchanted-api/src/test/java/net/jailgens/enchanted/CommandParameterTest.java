package net.jailgens.enchanted;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandParameterTest {

    @ValueSource(strings = {
            "valid-name",
            "valid_name",
            "validName",
            "validName123",
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-!@#$%^&*()_+-=[]{};':\",./<>?"})
    @ParameterizedTest
    void Given_ValidName_When_MatchingPattern_Then_Matches(final String name) {

        final boolean matches = name.matches(CommandParameter.NAME_PATTERN);

        assertTrue(matches);
    }

    @ValueSource(strings = "this has spaces")
    @ParameterizedTest
    void Given_InvalidName_When_MatchingPattern_Then_DoesNotMatch(final String name) {

        final boolean matches = name.matches(CommandParameter.NAME_PATTERN);

        assertTrue(matches);
    }
}
