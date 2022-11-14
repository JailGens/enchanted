package net.jailgens.enchanted;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandMapImplTest {

    CommandMap commandMap;

    @BeforeEach
    void setUp() {

        commandMap = CommandMap.create();
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullCommand_When_RegisterCommand_Then_Throws() {

        assertThrows(NullPointerException.class, () -> commandMap.registerCommand(null));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullCommand_When_UnregisterCommand_Then_Throws() {

        assertThrows(NullPointerException.class, () -> commandMap.unregisterCommand(null));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullCommand_When_GetCommand_Then_Throws() {

        assertThrows(NullPointerException.class, () -> commandMap.getCommand(null));
    }

    @Test
    void Given_Command_When_RegisterCommand_Then_CommandIsRegisteredWithLabels() {

        final Command command = mock(Command.class);
        when(command.getLabels()).thenReturn(List.of("test"));

        commandMap.registerCommand(command);

        assertTrue(commandMap.getCommand("test").isPresent());
    }

    @Test
    void Given_Command_When_RegisterCommandTwice_Then_Throws() {

        final Command command = mock(Command.class);
        when(command.getLabels()).thenReturn(List.of("test"));

        commandMap.registerCommand(command);

        assertThrows(IllegalArgumentException.class, () -> commandMap.registerCommand(command));
    }

    @Test
    void Given_Command_When_RegisterCommand_Then_CommandIsRegisteredWithAliases() {

        final Command command = mock(Command.class);
        when(command.getLabels()).thenReturn(List.of("test"));

        commandMap.registerCommand(command);

        assertEquals(Optional.of(command), commandMap.getCommand("test"));
    }

    @Test
    void Given_Command_When_UnregisterCommand_Then_CommandIsUnregistered() {

        final Command command = mock(Command.class);
        when(command.getLabels()).thenReturn(List.of("test"));

        commandMap.registerCommand(command);
        commandMap.unregisterCommand(command);

        assertFalse(commandMap.getRegisteredCommands().contains(command));
    }

    @Test
    void Given_UnregisteredCommand_When_UnregisterCommand_Then_Throws() {

        final Command command = mock(Command.class);
        when(command.getLabels()).thenReturn(List.of("test"));

        assertThrows(IllegalArgumentException.class, () -> commandMap.unregisterCommand(command));
    }

    @Test
    void Given_CommandMapWithRegisteredCommand_When_UnregisterCommandWithSameName_Then_Throws() {

        final Command command1 = mock(Command.class);
        final Command command2 = mock(Command.class);
        when(command1.getLabels()).thenReturn(List.of("test"));
        when(command2.getLabels()).thenReturn(List.of("test"));
        commandMap.registerCommand(command1);

        assertThrows(IllegalArgumentException.class, () -> commandMap.unregisterCommand(command2));
        assertEquals(Optional.of(command1), commandMap.getCommand("test"));
    }

    @Test
    void Given_Command_When_GetRegisteredCommands_Then_ReturnsRegisteredCommands() {

        final Command command = mock(Command.class);
        when(command.getLabels()).thenReturn(List.of("label"));

        commandMap.registerCommand(command);

        assertEquals(List.of(command), commandMap.getRegisteredCommands());
    }
}
