package net.jailgens.enchanted;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandTest {

    @ValueSource(strings = {
            "valid-name",
            "valid_name",
            "validName",
            "validName123",
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-"})
    @ParameterizedTest
    void Given_ValidName_When_MatchingPattern_Then_Matches(final String name) {

        final boolean matches = name.matches(Command.NAME_PATTERN);

        assertTrue(matches);
    }

    @ValueSource(strings = {
            "this has spaces",
            "special!character",
            "special@character",
            "hasNewLine\n"})
    @ParameterizedTest
    void Given_InvalidName_When_MatchingPattern_Then_DoesNotMatch(final String name) {

        final boolean matches = name.matches(Command.NAME_PATTERN);

        assertFalse(matches);
    }

    @ValueSource(strings = {
            "valid description",
            "valid description with spaces",
            "valid description with special characters !@#$%^&*()_+-=[]{};':\",./<>?"})
    @ParameterizedTest
    void Given_ValidDescription_When_MatchingPattern_Then_Matches(final String description) {

        final boolean matches = description.matches(Command.DESCRIPTION_PATTERN);

        assertTrue(matches);
    }

    @ValueSource(strings = {"invalid description\n", "\n\n\n\n"})
    @ParameterizedTest
    void Given_InvalidDescription_When_MatchingPattern_Then_DoesNotMatch(final String description) {

        final boolean matches = description.matches(Command.DESCRIPTION_PATTERN);

        assertFalse(matches);
    }
}
