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

class SharedCommandRegistryTest {

    CommandFactory commandFactory;
    CommandRegistry registry;

    @BeforeEach
    void setUp() {

        commandFactory = mock(CommandFactory.class);
        registry = new SharedCommandRegistry(commandFactory);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullCommand_When_RegisterCommand_Then_Throws() {

        assertThrows(NullPointerException.class, () -> registry.registerCommand(null));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullCommand_When_UnregisterCommand_Then_Throws() {

        assertThrows(NullPointerException.class, () -> registry.unregisterCommand(null));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullCommand_When_GetCommand_Then_Throws() {

        assertThrows(NullPointerException.class, () -> registry.getCommand(null));
    }

    @Test
    void Given_Command_When_RegisterCommand_Then_ReturnsCommand() {

        final Object command = new Object();
        final Command commandInstance = mock(Command.class);
        when(commandInstance.getLabels()).thenReturn(List.of("label"));
        when(commandFactory.createCommand(command)).thenReturn(commandInstance);

        final Command createdCommand = registry.registerCommand(command);

        assertEquals(commandInstance, createdCommand);
    }

    @Test
    void Given_Command_When_RegisterCommand_Then_CommandIsRegisteredWithLabels() {

        final Object command = new Object();
        final Command commandInstance = mock(Command.class);
        when(commandInstance.getLabels()).thenReturn(List.of("test"));
        when(commandFactory.createCommand(command)).thenReturn(commandInstance);

        registry.registerCommand(command);

        assertTrue(registry.getCommand("test").isPresent());
    }

    @Test
    void Given_Command_When_RegisterCommandTwice_Then_Throws() {

        final Object command = new Object();
        final Command commandInstance = mock(Command.class);
        when(commandInstance.getLabels()).thenReturn(List.of("test"));
        when(commandFactory.createCommand(command)).thenReturn(commandInstance);

        registry.registerCommand(command);

        assertThrows(IllegalArgumentException.class, () -> registry.registerCommand(command));
    }

    @Test
    void Given_Command_When_RegisterCommand_Then_CommandIsRegisteredWithAliases() {

        final Object command = new Object();
        final Command commandInstance = mock(Command.class);
        when(commandInstance.getLabels()).thenReturn(List.of("test"));
        when(commandFactory.createCommand(command)).thenReturn(commandInstance);

        registry.registerCommand(command);

        assertEquals(Optional.of(commandInstance), registry.getCommand("test"));
    }

    @Test
    void Given_Command_When_UnregisterCommand_Then_CommandIsUnregistered() {

        final Object command = new Object();
        final Command commandInstance = mock(Command.class);
        when(commandInstance.getLabels()).thenReturn(List.of("test"));
        when(commandFactory.createCommand(command)).thenReturn(commandInstance);

        registry.registerCommand(command);
        registry.unregisterCommand(commandInstance);


        assertFalse(registry.getRegisteredCommands().contains(commandInstance));
    }

    @Test
    void Given_UnregisteredCommand_When_UnregisterCommand_Then_Throws() {

        final Object command = new Object();
        final Command commandInstance = mock(Command.class);
        when(commandInstance.getLabels()).thenReturn(List.of("test"));
        when(commandFactory.createCommand(command)).thenReturn(commandInstance);

        assertThrows(IllegalArgumentException.class, () -> registry.unregisterCommand(commandInstance));
    }

    @Test
    void Given_CommandRegistryWithRegisteredCommand_When_UnregisterCommandWithSameName_Then_Throws() {

        final Object command = new Object();
        final Command commandInstance = mock(Command.class);
        final Object otherCommand = new Object();
        final Command otherCommandInstance = mock(Command.class);
        when(otherCommandInstance.getLabels()).thenReturn(List.of("test"));
        when(commandInstance.getLabels()).thenReturn(List.of("test"));
        when(commandFactory.createCommand(otherCommand)).thenReturn(otherCommandInstance);
        when(commandFactory.createCommand(command)).thenReturn(commandInstance);

        registry.registerCommand(otherCommand);

        assertThrows(IllegalArgumentException.class, () -> registry.unregisterCommand(commandInstance));
    }

    @Test
    void Given_Command_When_GetRegisteredCommands_Then_ReturnsRegisteredCommands() {

        final Object command = new Object();
        final Command commandInstance = mock(Command.class);
        when(commandInstance.getLabels()).thenReturn(List.of("label"));
        when(commandFactory.createCommand(command)).thenReturn(commandInstance);

        registry.registerCommand(command);

        assertEquals(List.of(commandInstance), registry.getRegisteredCommands());
    }
}
