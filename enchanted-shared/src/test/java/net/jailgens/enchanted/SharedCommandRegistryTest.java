package net.jailgens.enchanted;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SharedCommandRegistryTest {

    CommandFactory commandFactory;
    CommandMap<Command> commandMap;
    CommandRegistry registry;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {

        commandFactory = mock(CommandFactory.class);
        commandMap = mock(CommandMap.class);
        registry = new SharedCommandRegistry(commandFactory, commandMap);
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
        final CommandGroup commandInstance = mock(CommandGroup.class);
        when(commandInstance.getLabels()).thenReturn(List.of("label"));
        when(commandFactory.createCommand(command)).thenReturn(commandInstance);

        final Command createdCommand = registry.registerCommand(command);

        verify(commandFactory).createCommand(command);
        verify(commandMap).registerCommand(commandInstance);
        assertEquals(commandInstance, createdCommand);
    }

    @Test
    void Given_CommandRegistry_When_RegisterCommand_Then_RegistersCommand() {

        final CommandGroup command = mock(CommandGroup.class);
        when(commandFactory.createCommand(any())).thenReturn(command);

        registry.registerCommand(new Object());

        verify(commandMap).registerCommand(command);
    }

    @Test
    void Given_CommandRegistry_When_UnregisterCommand_Then_UnregistersCommand() {

        final Command command = mock(Command.class);

        registry.unregisterCommand(command);

        verify(commandMap).unregisterCommand(command);
    }

    @Test
    void Given_CommandRegistry_When_GetCommand_Then_ReturnsCommand() {

        final Command command = mock(Command.class);
        when(commandMap.getCommand("label")).thenAnswer((__) -> Optional.of(command));

        final Optional<? extends Command> foundCommand = registry.getCommand("label");

        assertEquals(Optional.of(command), foundCommand);
    }

    @Test
    void Given_CommandRegistry_When_GetCommand_Then_ReturnsEmpty() {

        when(commandMap.getCommand("label")).thenAnswer((__) -> Optional.empty());

        final Optional<? extends Command> foundCommand = registry.getCommand("not-label");

        assertEquals(Optional.empty(), foundCommand);
    }

    @Test
    void Given_CommandRegistry_When_GetRegisteredCommands_Then_ReturnsCommands() {

        final Command command = mock(Command.class);
        when(commandMap.getRegisteredCommands()).thenAnswer((__) -> List.of(command));

        final Collection<? extends Command> registeredCommands = registry.getRegisteredCommands();

        assertEquals(List.of(command), registeredCommands);
    }

    @Test
    void Given_EmptyCommandRegistry_When_GetRegisteredCommands_Then_ReturnsEmpty() {

        when(commandMap.getRegisteredCommands()).thenAnswer((__) -> List.of());

        final Collection<? extends Command> registeredCommands = registry.getRegisteredCommands();

        assertTrue(registeredCommands.isEmpty());
    }
}
