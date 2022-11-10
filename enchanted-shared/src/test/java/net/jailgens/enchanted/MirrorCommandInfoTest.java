package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Aliases;
import net.jailgens.enchanted.annotations.Command;
import net.jailgens.enchanted.annotations.Description;
import net.jailgens.enchanted.annotations.Usage;
import net.jailgens.mirror.Mirror;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MirrorCommandInfoTest {

    Mirror mirror;

    @BeforeEach
    void setUp() {

        mirror = Mirror.builder().build();
    }

    @Test
    void Given_MirrorCommandInfoWithoutNameAnnotation_When_GetName_Then_ReturnsEmptyOptional() {

        class TestCommandInfo {

        }
        final CommandInfo commandInfo =
                new MirrorCommandInfo(mirror.reflect(TestCommandInfo.class).getAnnotations());

        final Optional<String> name = commandInfo.getName();

        assertEquals(Optional.empty(), name);
    }

    @SuppressWarnings("PatternValidation")
    @Test
    void Given_MirrorCommandInfoWithName_When_GetName_Then_ReturnsName() {

        @Command("very cool name")
        class TestCommandInfo {

        }
        final CommandInfo commandInfo =
                new MirrorCommandInfo(mirror.reflect(TestCommandInfo.class).getAnnotations());

        final Optional<String> name = commandInfo.getName();

        assertEquals(Optional.of("very cool name"), name);
    }

    @Test
    void Given_MirrorCommandInfoWithoutAliases_When_GetAliases_Then_ReturnsEmptyList() {

        class TestCommandInfo {

        }
        final CommandInfo commandInfo =
                new MirrorCommandInfo(mirror.reflect(TestCommandInfo.class).getAnnotations());

        final List<String> aliases = commandInfo.getAliases();

        assertEquals(List.of(), aliases);
    }

    @Test
    void Given_MirrorCommandWithAliases_When_GetAliases_Then_ReturnsAliases() {

        @Aliases({"alias 1", "alias 2"})
        class TestCommandInfo {

        }
        final CommandInfo commandInfo =
                new MirrorCommandInfo(mirror.reflect(TestCommandInfo.class).getAnnotations());

        final List<String> aliases = commandInfo.getAliases();

        assertEquals(List.of("alias 1", "alias 2"), aliases);
    }

    @Test
    void Given_MirrorCommandWithoutUsage_When_GetUsage_Then_ReturnsEmptyOptional() {

        class TestCommandInfo {

        }
        final CommandInfo commandInfo =
                new MirrorCommandInfo(mirror.reflect(TestCommandInfo.class).getAnnotations());

        final Optional<String> usage = commandInfo.getUsage();

        assertEquals(Optional.empty(), usage);
    }

    @Test
    void Given_MirrorCommandWithUsage_When_GetUsage_Then_ReturnsUsage() {

        @Usage("test usage")
        class TestCommandInfo {

        }
        final CommandInfo commandInfo =
                new MirrorCommandInfo(mirror.reflect(TestCommandInfo.class).getAnnotations());

        final Optional<String> usage = commandInfo.getUsage();

        assertEquals(Optional.of("test usage"), usage);
    }
    @Test
    void Given_MirrorCommandWithDescription_When_GetDescription_Then_ReturnsEmptyOptional() {

        class TestCommandInfo {

        }
        final CommandInfo commandInfo =
                new MirrorCommandInfo(mirror.reflect(TestCommandInfo.class).getAnnotations());

        final Optional<String> description = commandInfo.getDescription();

        assertEquals(Optional.empty(), description);
    }

    @SuppressWarnings("PatternValidation")
    @Test
    void Given_MirrorCommandWithDescription_When_GetDescription_Then_ReturnsDescription() {

        @Description("cool\ndescription")
        class TestCommandInfo {

        }
        final CommandInfo commandInfo =
                new MirrorCommandInfo(mirror.reflect(TestCommandInfo.class).getAnnotations());

        final Optional<String> description = commandInfo.getDescription();

        assertEquals(Optional.of("cool\ndescription"), description);
    }
}
