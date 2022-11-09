package net.jailgens.enchanted;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmptyUsageGeneratorTest {

    UsageGenerator usageGenerator;

    @BeforeEach
    void setUp() {

        usageGenerator = new EmptyUsageGenerator();
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_EmptyUsageGenerator_When_GenerateUsage_Then_ReturnsEmptyString() {

        final String usage = usageGenerator.generateUsage(null);

        assertEquals("", usage);
    }
}
