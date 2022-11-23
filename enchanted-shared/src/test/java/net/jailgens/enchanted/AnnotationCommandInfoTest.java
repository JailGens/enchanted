package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Aliases;
import net.jailgens.enchanted.annotations.Command;
import net.jailgens.enchanted.annotations.Description;
import net.jailgens.mirror.AnnotationElement;
import net.jailgens.mirror.AnnotationValues;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnnotationCommandInfoTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullAnnotations_When_CreateAnnotationCommandInfo_Then_Throws() {

        assertThrows(NullPointerException.class, () -> new AnnotationCommandInfo(null));
    }

    @Test
    void Given_EmptyAnnotationValues_When_CreateAnnotationCommandInfo_Then_Throws() {

        final AnnotationValues annotations = AnnotationValues.empty();

        assertThrows(IllegalArgumentException.class, () -> new AnnotationCommandInfo(annotations));
    }

    @Test
    void Given_AnnotationsWithInvalidName_When_CreateAnnotationCommandInfo_Then_Throws() {

        final AnnotationValues annotations = AnnotationValues.builder()
                .value(AnnotationElement.value(Command.class), "invalid name")
                .build();

        assertThrows(IllegalArgumentException.class, () -> new AnnotationCommandInfo(annotations));
    }

    @Test
    void Given_AnnotationsWithInvalidDescription_When_CreateAnnotationCommandInfo_Then_Throws() {

        final AnnotationValues annotations = AnnotationValues.builder()
                .value(AnnotationElement.value(Command.class), "valid-name")
                .value(AnnotationElement.value(Description.class), "invalid\ndescription")
                .build();

        assertThrows(IllegalArgumentException.class, () -> new AnnotationCommandInfo(annotations));
    }

    @Test
    void Given_AnnotationCommandInfoWithName_When_GetName_Then_ReturnsName() {

        final CommandInfo commandInfo = new AnnotationCommandInfo(AnnotationValues.builder()
                .value(AnnotationElement.value(Command.class), "valid-name")
                .build());

        final String name = commandInfo.getName();

        assertEquals("valid-name", name);
    }

    @Test
    void Given_AnnotationCommandInfoWithAliases_When_GetAliases_Then_ReturnsAliases() {

        final CommandInfo commandInfo = new AnnotationCommandInfo(AnnotationValues.builder()
                .value(AnnotationElement.value(Command.class), "valid-name")
                .value(AnnotationElement.value(Aliases.class), "alias1", "alias2")
                .build());

        final List<String> aliases = commandInfo.getAliases();

        assertEquals(List.of("alias1", "alias2"), aliases);
    }

    @Test
    void Given_AnnotationCommandInfoWithoutAliases_When_GetAliases_Then_ReturnsEmptyList() {

        final CommandInfo commandInfo = new AnnotationCommandInfo(AnnotationValues.builder()
                .value(AnnotationElement.value(Command.class), "valid-name")
                .build());

        final List<String> aliases = commandInfo.getAliases();

        assertEquals(List.of(), aliases);
    }

    @Test
    void Given_AnnotationCommandInfo_When_GetLabels_Then_ReturnsLabels() {

        final CommandInfo commandInfo = new AnnotationCommandInfo(AnnotationValues.builder()
                .value(AnnotationElement.value(Command.class), "valid-name")
                .value(AnnotationElement.value(Aliases.class), "alias1", "alias2")
                .build());

        final List<String> labels = commandInfo.getLabels();

        assertEquals(List.of("valid-name", "alias1", "alias2"), labels);
    }

    @Test
    void Given_AnnotationCommandInfoWithoutAliases_When_GetLabels_Then_ReturnsName() {

        final CommandInfo commandInfo = new AnnotationCommandInfo(AnnotationValues.builder()
                .value(AnnotationElement.value(Command.class), "valid-name")
                .build());

        final List<String> labels = commandInfo.getLabels();

        assertEquals(List.of("valid-name"), labels);
    }

    @Test
    void Given_AnnotationCommandInfoWithDescription_When_GetDescription_Then_ReturnsDescription() {

        final CommandInfo commandInfo = new AnnotationCommandInfo(AnnotationValues.builder()
                .value(AnnotationElement.value(Command.class), "valid-name")
                .value(AnnotationElement.value(Description.class), "valid description")
                .build());

        final String description = commandInfo.getDescription();

        assertEquals("valid description", description);
    }

    @Test
    void Given_AnnotationCommandInfoWithoutDescription_When_GetDescription_Then_ReturnsEmptyString() {

        final CommandInfo commandInfo = new AnnotationCommandInfo(AnnotationValues.builder()
                .value(AnnotationElement.value(Command.class), "valid-name")
                .build());

        final String description = commandInfo.getDescription();

        assertEquals("", description);
    }
}
