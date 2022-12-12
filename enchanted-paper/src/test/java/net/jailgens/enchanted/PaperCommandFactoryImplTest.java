package net.jailgens.enchanted;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PaperCommandFactoryImplTest {

    CommandFactory commandFactory;
    PaperAdapter adapter;
    PaperCommandFactory paperCommandFactory;

    @BeforeEach
    void setUp() {

        commandFactory = mock(CommandFactory.class);
        adapter = mock(PaperAdapter.class);
        paperCommandFactory = new PaperCommandFactoryImpl(commandFactory, adapter);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testNullCommandFactory() {

        assertThrows(NullPointerException.class, () -> new PaperCommandFactoryImpl(null, adapter));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testNullAdapter() {

        assertThrows(NullPointerException.class, () -> new PaperCommandFactoryImpl(commandFactory, null));
    }

    @Test
    void testCreateCommand() {

        final Object command = new Object();
        final CommandGroup commandGroup = mock(CommandGroup.class);
        final PaperCommandGroup adaptedCommandGroup = mock(PaperCommandGroup.class);

        when(commandFactory.createCommand(command)).thenReturn(commandGroup);
        when(adapter.adapt(commandGroup)).thenReturn(adaptedCommandGroup);

        final PaperCommandGroup createdCommand = paperCommandFactory.createCommand(command);

        assertEquals(adaptedCommandGroup, createdCommand);
    }
}
