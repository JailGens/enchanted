package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Aliases;
import net.jailgens.enchanted.annotations.Command;
import net.jailgens.enchanted.annotations.Description;
import net.jailgens.enchanted.annotations.Usage;
import net.jailgens.mirror.Mirror;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unused")
class SharedCommandFactoryTest {

    UsageGenerator usageGenerator;
    MethodCommandFactory methodFactory;
    SharedCommandFactory commandFactory;

    @BeforeEach
    void setUp() {

        usageGenerator = mock(UsageGenerator.class);
        methodFactory = mock(MethodCommandFactory.class);
        commandFactory = new SharedCommandFactory(Mirror.builder().build(),
                usageGenerator, methodFactory);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullMirror_When_CreateCommand_Then_Throws() {

        assertThrows(NullPointerException.class,
                () -> new SharedCommandFactory(null, usageGenerator, methodFactory));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullSyntaxGenerator_When_CreateCommand_Then_Throws() {

        assertThrows(NullPointerException.class,
                () -> new SharedCommandFactory(Mirror.builder().build(), null, methodFactory));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullMethodFactory_When_CreateCommand_Then_Throws() {

        assertThrows(NullPointerException.class,
                () -> new SharedCommandFactory(Mirror.builder().build(), usageGenerator, null));
    }


    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullCommand_When_CreateCommand_Then_Throws() {

        assertThrows(NullPointerException.class, () -> commandFactory.createCommand(null));
    }

    @Test
    void Given_CommandWithName_When_CreateCommand_Then_GetNameReturnsName() {

        @Command("test")
        class TestCommand {

        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        assertEquals("test", command.getName());
    }

    @Test
    void Given_CommandWithoutName_When_CreateCommand_Then_Throws() {

        class TestCommand {

        }

        assertThrows(IllegalArgumentException.class,
                () -> commandFactory.createCommand(new TestCommand()));
    }

    @SuppressWarnings("PatternValidation")
    @Test
    void Given_CommandWithInvalidName_When_CreateCommand_Then_Throws() {

        @Command("invalid name")
        class TestCommand {

        }

        assertThrows(IllegalArgumentException.class,
                () -> commandFactory.createCommand(new TestCommand()));
    }

    @Test
    void Given_CommandWithAliases_When_CreateCommand_Then_GetAliasesReturnsAliases() {

        @Command("test")
        @Aliases({"alias1", "alias2"})
        class TestCommand {

        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        assertEquals(List.of("alias1", "alias2"), command.getAliases());
    }

    @Test
    void Given_CommandWithAliasAsName_When_CreateCommand_Then_Throws() {

        @Command("test")
        @Aliases({"test"})
        class TestCommand {

        }

        assertThrows(IllegalArgumentException.class,
                () -> commandFactory.createCommand(new TestCommand()));
    }

    @Test
    void Given_CommandWithoutAliases_When_CreateCommand_Then_GetAliasesReturnsEmptyList() {

        @Command("test")
        class TestCommand {

        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        assertEquals(List.of(), command.getAliases());
    }

    @Test
    void Given_CommandWithInvalidAlias_When_CreateCommand_Then_Throws() {

        @Command("test")
        @Aliases({"invalid alias"})
        class TestCommand {

        }

        assertThrows(IllegalArgumentException.class,
                () -> commandFactory.createCommand(new TestCommand()));
    }

    @Test
    void Given_CommandWithNameAndAliases_When_CreateCommand_Then_GetLabelsReturnsLabels() {

        @Command("test")
        @Aliases({"alias1", "alias2"})
        class TestCommand {

        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        assertEquals(List.of("test", "alias1", "alias2"), command.getLabels());
    }

    @Test
    void Given_CommandWithNameAndNoAliases_When_CreateCommand_Then_GetLabelsReturnsName() {

        @Command("test")
        class TestCommand {

        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        assertEquals(List.of("test"), command.getLabels());
    }

    @Test
    void Given_CommandWithUsage_When_CreateCommand_Then_GetUsageReturnsUsage() {

        @Command("test")
        @Usage("explicit usage")
        class TestCommand {

        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        assertEquals("explicit usage", command.getUsage());
    }

    @Test
    void Given_CommandWithoutUsage_When_CreateCommand_Then_GetUsageReturnsGeneratedUsage() {

        when(usageGenerator.generateUsage(any())).thenReturn("generated usage");
        @Command("test")
        class TestCommand {

        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        assertEquals("generated usage", command.getUsage());
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "ConstantConditions"})
    @Test
    void Given_Command_When_GetDescriptionWithNull_Then_Throws() {

        @Command("test")
        class TestCommand {

        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        assertThrows(NullPointerException.class, () -> command.getDescription(null));
    }

    @Test
    void Given_CommandWithDescription_When_CreateCommand_Then_GetDescriptionReturnsDescription() {

        @Command("test")
        @Description("test description")
        class TestCommand {

        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        assertEquals("test description", command.getDescription(Locale.ENGLISH));
        assertEquals("test description", command.getDescription());
    }

    @Test
    void Given_CommandWithoutDescription_When_CreateCommand_Then_GetDescriptionReturnsEmptyString() {

        @Command("test")
        class TestCommand {

        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        assertEquals("", command.getDescription(Locale.ENGLISH));
        assertEquals("", command.getDescription());
    }

    @SuppressWarnings("PatternValidation")
    @Test
    void Given_CommandWithInvalidDescription_When_CreateCommand_Then_Throws() {

        @Command("test")
        @Description("invalid\ndescription")
        class TestCommand {

        }

        assertThrows(IllegalArgumentException.class,
                () -> commandFactory.createCommand(new TestCommand()));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_Command_When_ExecuteWithNullSender_Then_Throws() {

        @Command("test")
        class TestCommand {

        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        assertThrows(NullPointerException.class, () -> command.execute(null, List.of()));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_Command_When_ExecuteWithNullArgs_Then_Throws() {

        @Command("test")
        class TestCommand {

        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        assertThrows(NullPointerException.class, () -> command.execute(mock(CommandExecutor.class), null));
    }

    @Test
    void Given_CommandWithDefaultMethod_When_Execute_Then_DefaultMethodExecutes() {

        final CommandExecutor executor = mock(CommandExecutor.class);
        final net.jailgens.enchanted.Command defaultCommand =
                mock(net.jailgens.enchanted.Command.class);
        when(defaultCommand.execute(any(), any())).thenReturn(CommandResult.success("success message"));
        when(methodFactory.createCommand(any(), argThat((method) -> method.getName().equals("defaultMethod"))))
                .thenReturn(defaultCommand);
        @Command("test")
        class TestCommand {

            @Command.Default
            void defaultMethod() {

            }
        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        final CommandResult result = command.execute(executor, List.of("1", "2"));

        assertEquals(CommandResult.success("success message"), result);
        verify(defaultCommand).execute(executor, List.of("1", "2"));
    }

    @Test
    void Given_CommandWithSubCommands_When_Execute_Then_SubCommandExecutes() {

        final CommandExecutor executor = mock(CommandExecutor.class);
        final net.jailgens.enchanted.Command subCommand =
                mock(net.jailgens.enchanted.Command.class);
        when(subCommand.getLabels()).thenReturn(List.of("sub"));
        when(subCommand.execute(any(), any())).thenReturn(CommandResult.success("success message"));
        when(methodFactory.createCommand(any(), any()))
                .thenReturn(subCommand);
        @Command("test")
        class TestCommand {

            @Command("sub")
            void subCommand() {

            }
        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        final CommandResult result = command.execute(executor, List.of("sub", "1", "2"));

        assertEquals(CommandResult.success("success message"), result);
        verify(subCommand).execute(executor, List.of("1", "2"));
    }

    @Test
    void Given_CommandWithDefaultAndSubCommands_When_ExecuteWithoutSubCommand_Then_DefaultMethodExecutes() {

        final CommandExecutor executor = mock(CommandExecutor.class);
        final net.jailgens.enchanted.Command defaultCommand =
                mock(net.jailgens.enchanted.Command.class);
        when(defaultCommand.execute(any(), any())).thenReturn(CommandResult.success("success message"));
        when(methodFactory.createCommand(any(), any()))
                .thenReturn(defaultCommand);
        @Command("test")
        class TestCommand {

            @Command.Default
            void defaultMethod() {

            }

            @Command("sub")
            void subCommand() {

            }
        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        final CommandResult result = command.execute(executor, List.of("1", "2"));

        assertEquals(CommandResult.success("success message"), result);
        verify(defaultCommand).execute(executor, List.of("1", "2"));
    }

    @Test
    void Given_CommandWithDefaultAndSubCommands_When_ExecuteWithSubCommand_Then_SubCommandExecutes() {

        final CommandExecutor executor = mock(CommandExecutor.class);
        final net.jailgens.enchanted.Command subCommand =
                mock(net.jailgens.enchanted.Command.class);
        when(subCommand.getLabels()).thenReturn(List.of("sub"));
        when(subCommand.execute(any(), any())).thenReturn(CommandResult.success("success message"));
        when(methodFactory.createCommand(any(), any()))
                .thenReturn(subCommand);
        @Command("test")
        class TestCommand {

            @Command.Default
            void defaultMethod() {

            }

            @Command("sub")
            void subCommand() {

            }
        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        final CommandResult result = command.execute(executor, List.of("sub", "1", "2"));

        assertEquals(CommandResult.success("success message"), result);
        verify(subCommand).execute(executor, List.of("1", "2"));
    }

    @Test
    void Given_CommandWithMultipleDefaultCommands_When_Execute_Then_Throws() {

        @Command("test")
        class TestCommand {

            @Command.Default
            void defaultMethod1() {

            }

            @Command.Default
            void defaultMethod2() {

            }
        }

        assertThrows(IllegalArgumentException.class,
                () -> commandFactory.createCommand(new TestCommand()));
    }

    @Test
    void Given_CommandWithMultipleSubCommandsWithSameName_When_Execute_Then_Throws() {
        final net.jailgens.enchanted.Command subCommand =
                mock(net.jailgens.enchanted.Command.class);
        when(subCommand.getLabels()).thenReturn(List.of("sub"));
        when(methodFactory.createCommand(any(), any()))
                .thenReturn(subCommand);
        @Command("test")
        class TestCommand {

            @Command("sub")
            void subCommand1() {

            }

            @Command("sub")
            void subCommand2() {

            }
        }

        assertThrows(IllegalArgumentException.class,
                () -> commandFactory.createCommand(new TestCommand()));
    }

    @Test
    void Given_CommandWithDefaultMethod_When_ExecuteWithNoArgs_Then_DefaultMethodExecutes() {

        final CommandExecutor executor = mock(CommandExecutor.class);
        final net.jailgens.enchanted.Command defaultCommand =
                mock(net.jailgens.enchanted.Command.class);
        when(defaultCommand.execute(any(), any())).thenReturn(CommandResult.success("success message"));
        when(methodFactory.createCommand(any(), any()))
                .thenReturn(defaultCommand);
        @Command("test")
        class TestCommand {

            @Command.Default
            void defaultMethod() {

            }
        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        final CommandResult result = command.execute(executor, List.of());

        assertEquals(CommandResult.success("success message"), result);
        verify(defaultCommand).execute(executor, List.of());
    }

    @Test
    void Given_Command_When_ExecuteWithInvalidSubCommand_Then_ReturnsError() {

        final net.jailgens.enchanted.Command subCommand =
                mock(net.jailgens.enchanted.Command.class);
        when(subCommand.getLabels()).thenReturn(List.of("sub"));
        when(methodFactory.createCommand(any(), any()))
                .thenReturn(subCommand);
        final CommandExecutor executor = mock(CommandExecutor.class);
        @Command("test")
        class TestCommand {

            @Command("sub")
            void subCommand() {

            }
        }

        final net.jailgens.enchanted.Command command = commandFactory.createCommand(new TestCommand());

        final CommandResult result = command.execute(executor, List.of("invalid", "1", "2"));

        assertFalse(result.isSuccess());
        assertTrue(result.isError());
    }
}
