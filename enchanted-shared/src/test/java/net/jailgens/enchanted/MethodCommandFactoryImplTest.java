package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Aliases;
import net.jailgens.enchanted.annotations.Command;
import net.jailgens.enchanted.annotations.Description;
import net.jailgens.enchanted.annotations.Join;
import net.jailgens.enchanted.annotations.Usage;
import net.jailgens.mirror.Method;
import net.jailgens.mirror.Mirror;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"unused", "ResultOfMethodCallIgnored"})
class MethodCommandFactoryImplTest {

    Mirror mirror;
    MethodCommandFactory factory;
    ConverterRegistry converterRegistry;
    UsageGenerator usageGenerator;

    @BeforeEach
    void setUp() {

        mirror = Mirror.builder().build();
        usageGenerator = mock(UsageGenerator.class);
        converterRegistry = mock(ConverterRegistry.class);
        factory = new MethodCommandFactoryImpl(converterRegistry, usageGenerator);
    }

    @SuppressWarnings("unchecked")
    <T extends @NotNull Object>
    @NotNull Method<@NotNull T, Void> getMethod(
            final @NotNull Class<@NotNull ? extends T> cls) {

        return (Method<T, Void>) mirror.reflect(cls).getMethods().stream()
                .filter(method -> method.getName().equals("command"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such method: " + "command"));
    }

    <T extends @NotNull Object> net.jailgens.enchanted.@NotNull Command createCommand(final @NotNull T command) {

        return factory.createCommand(command, getMethod(command.getClass()));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullConverterRegistry_When_CreateCommand_Then_Throws() {

        assertThrows(NullPointerException.class, () -> new MethodCommandFactoryImpl(null, usageGenerator));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullUsageGenerator_When_CreateCommand_Then_Throws() {

        assertThrows(NullPointerException.class, () -> new MethodCommandFactoryImpl(converterRegistry, null));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullMethod_When_CreateCommand_Then_Throws() {

        assertThrows(NullPointerException.class, () -> factory.createCommand(new Object(), null));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_NullCommand_When_CreateCommand_Then_Throws() {

        class TestClass {

            public void command() {

            }
        }

        assertThrows(NullPointerException.class, () -> factory.createCommand(null, getMethod(TestClass.class)));
    }

    @Test
    void Given_DefaultCommand_When_CreateCommand_Then_GetNameReturnsDefault() {

        class TestCommand {

            @Command.Default
            public void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertEquals("default", command.getName());
    }

    @Test
    void Given_DefaultCommandWithName_When_CreateCommand_Then_GetNameReturnsName() {

        class TestCommand {

            @Command("test")
            @Command.Default
            public void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertEquals("test", command.getName());
    }

    @Test
    void Given_CommandWithName_When_CreateCommand_Then_GetNameReturnsName() {

        class TestCommand {

            @Command("test")
            void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertEquals("test", command.getName());
    }

    @Test
    void Given_CommandWithoutName_When_CreateCommand_Then_Throws() {

        class TestCommand {

            void command() {

            }
        }

        assertThrows(IllegalArgumentException.class, () -> createCommand(new TestCommand()));
    }

    @SuppressWarnings("PatternValidation")
    @Test
    void Given_CommandWithInvalidName_When_CreateCommand_Then_Throws() {

        class TestCommand {

            @Command("command name")
            void command() {

            }
        }

        assertThrows(IllegalArgumentException.class, () -> createCommand(new TestCommand()));
    }

    @Test
    void Given_CommandWithAliases_When_CreateCommand_Then_GetAliasesReturnsAliases() {

        class TestCommand {

            @Command("test")
            @Aliases({"alias1", "alias2"})
            void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertEquals(List.of("alias1", "alias2"), command.getAliases());
    }

    @Test
    void Given_CommandWithoutAliases_When_CreateCommand_Then_GetAliasesReturnsEmptyList() {

        class TestCommand {

            @Command("test")
            void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertEquals(List.of(), command.getAliases());
    }

    @Test
    void Given_CommandWithInvalidAliases_When_CreateCommand_Then_Throws() {

        class TestCommand {

            @Command("test")
            @Aliases({"invalid alias"})
            void command() {

            }
        }

        assertThrows(IllegalArgumentException.class, () -> createCommand(new TestCommand()));
    }

    @Test
    void Given_CommandWithNameAndAliases_When_CreateCommand_Then_GetLabelsReturnsLabels() {

        class TestCommand {

            @Command("test")
            @Aliases({"alias1", "alias2"})
            void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertEquals(List.of("test", "alias1", "alias2"), command.getLabels());
    }

    @Test
    void Given_CommandWithAliasAsName_When_CreateCommand_Then_Throws() {

        class TestCommand {

            @Command("alias")
            @Aliases({"alias"})
            void command() {

            }
        }

        assertThrows(IllegalArgumentException.class, () -> createCommand(new TestCommand()));
    }

    @Test
    void Given_CommandWithNameAndNoAliases_When_CreateCommand_Then_GetLabelsReturnsName() {

        class TestCommand {

            @Command("test")
            void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertEquals(List.of("test"), command.getLabels());
    }

    @Test
    void Given_CommandWithUsage_When_CreateCommand_Then_GetUsageReturnsUsage() {

        class TestCommand {

            @Command("test")
            @Usage("usage")
            void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertEquals("usage", command.getUsage());
    }

    @Test
    void Given_CommandWithoutUsage_When_CreateCommand_Then_GetUsageReturnsGeneratedUsage() {

        when(usageGenerator.generateUsage(any())).thenReturn("generated usage");
        class TestCommand {

            @Command("test")
            void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertEquals("generated usage", command.getUsage());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_Command_When_GetDescriptionWithNull_Then_Throws() {

        class TestCommand {

            @Command("test")
            void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertThrows(NullPointerException.class, () -> command.getDescription(null));
    }

    @Test
    void Given_CommandWithDescription_When_CreateCommand_Then_GetDescriptionReturnsDescription() {

        class TestCommand {

            @Command("test")
            @Description("description")
            void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertEquals("description", command.getDescription(Locale.ENGLISH));
        assertEquals("description", command.getDescription());
    }

    @Test
    void Given_CommandWithoutDescription_When_CreateCommand_Then_GetDescriptionReturnsEmptyString() {

        class TestCommand {

            @Command("test")
            void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertEquals("", command.getDescription(Locale.ENGLISH));
        assertEquals("", command.getDescription());
    }

    @SuppressWarnings("PatternValidation")
    @Test
    void Given_CommandWithInvalidDescription_When_CreateCommand_Then_Throws() {

        class TestCommand {

            @Command("test")
            @Description("invalid\ndescription")
            void command() {

            }
        }

        assertThrows(IllegalArgumentException.class, () -> createCommand(new TestCommand()));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_Command_When_ExecuteWithNullSender_Then_Throws() {

        class TestCommand {

            @Command("test")
            void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertThrows(NullPointerException.class, () -> command.execute(null, List.of()));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void Given_Command_When_ExecuteWithNullArgs_Then_Throws() {

        class TestCommand {

            @Command("test")
            void command() {

            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        assertThrows(NullPointerException.class,
                () -> command.execute(mock(CommandExecutor.class), null));
    }

    @Test
    void Given_CommandWithoutParameters_When_Execute_Then_InvokesMethod() {

        final AtomicBoolean ran = new AtomicBoolean();
        class TestCommand {

            @Command("test")
            void command() {

                ran.set(true);
            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        final CommandResult result = command.execute(mock(CommandExecutor.class), List.of());

        assertTrue(result.isSuccess());
        assertFalse(result.isError());
        assertTrue(ran.get());
    }

    @Test
    void Given_CommandSingleParameter_When_Execute_Then_ExecuteWithExecutor() {

        final AtomicReference<CommandExecutor> executorHandle = new AtomicReference<>();
        class TestCommand {

            @Command("test")
            void command(final @NotNull CommandExecutor executor) {

                executorHandle.set(executor);
            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        final CommandExecutor mockExecutor = mock(CommandExecutor.class);
        command.execute(mockExecutor, List.of());

        assertEquals(mockExecutor, executorHandle.get());
    }

    @Test
    void Given_CommandWithParameters_When_Execute_Then_ExecuteWithConverter() {

        final CommandExecutor executor = mock(CommandExecutor.class);
        final AtomicReference<CommandExecutor> executorHandle = new AtomicReference<>();
        final AtomicReference<String> argument1Handle = new AtomicReference<>();
        final AtomicInteger argument2Handle = new AtomicInteger();
        when(converterRegistry.getConverter(String.class)).thenReturn(Optional.of((__) -> Optional.of("argument1")));
        when(converterRegistry.getConverter(int.class)).thenReturn(Optional.of((__) -> Optional.of(10)));
        class TestCommand {

            @Command("test")
            void command(final @NotNull CommandExecutor executor,
                         final @NotNull String argument1,
                         final int argument2) {

                executorHandle.set(executor);
                argument1Handle.set(argument1);
                argument2Handle.set(argument2);
            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        final CommandResult result = command.execute(executor, List.of("argument1", "argument2"));

        assertTrue(result.isSuccess());
        assertFalse(result.isError());
        assertEquals(executor, executorHandle.get());
        assertEquals("argument1", argument1Handle.get());
        assertEquals(10, argument2Handle.get());
    }

    @Test
    void Given_CommandWithNonRegisteredConverter_When_CreateCommand_Then_Throws() {

        class TestCommand {

            @Command("test")
            void command(final @NotNull CommandExecutor executor, final @NotNull String argument) {

            }
        }

        assertThrows(IllegalStateException.class, () -> createCommand(new TestCommand()));
    }

    @Test
    void Given_Command_When_ExecuteWithInvalidSenderType_Then_ReturnsError() {

        abstract class CustomExecutor implements CommandExecutor {}
        final CommandExecutor executor = mock(CommandExecutor.class);
        class TestCommand {

            @Command("test")
            void command(final @NotNull CustomExecutor executor) {

                fail();
            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        final CommandResult result = command.execute(executor, List.of());

        assertTrue(result.isError());
        assertFalse(result.isSuccess());
    }

    @Test
    void Given_Command_When_ExecuteWithIncorrectArgumentCount_Then_ReturnsError() {

        when(converterRegistry.getConverter(String.class)).thenReturn(Optional.of(Optional::of));
        class TestCommand {

            @Command("test")
            void command(final @NotNull CommandExecutor executor, final @NotNull String arg) {

                fail();
            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        final CommandResult tooFewResult = command.execute(mock(CommandExecutor.class), List.of());
        final CommandResult tooManyResult = command.execute(mock(CommandExecutor.class),
                List.of("arg1", "arg2"));

        assertTrue(tooFewResult.isError());
        assertFalse(tooFewResult.isSuccess());
        assertTrue(tooManyResult.isError());
        assertFalse(tooManyResult.isSuccess());
    }

    @Test
    void Given_Command_When_ExecuteWithInvalidArgument_Then_ReturnsError() {

        when(converterRegistry.getConverter(int.class)).thenReturn(Optional.of((__) -> Optional.empty()));
        class TestCommand {

            @Command("test")
            void command(final @NotNull CommandExecutor executor, final int arg) {

                fail();
            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        final CommandResult result = command.execute(mock(CommandExecutor.class), List.of("arg"));

        assertTrue(result.isError());
        assertFalse(result.isSuccess());
    }
    
    @Test
    void Given_CommandWithOptionalParameter_When_ExecuteWithMissingArgument_Then_ExecuteWithNull() {

        final AtomicReference<String> argumentHandle = new AtomicReference<>("not null");
        when(converterRegistry.getConverter(String.class)).thenReturn(Optional.of((__) -> Optional.of("argument")));
        class TestCommand {

            @Command("test")
            void command(final @NotNull CommandExecutor executor, 
                         @net.jailgens.enchanted.annotations.Optional final @Nullable String argument) {

                argumentHandle.set(argument);
            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        final CommandResult result = command.execute(mock(CommandExecutor.class), List.of());

        assertTrue(result.isSuccess());
        assertFalse(result.isError());
        assertNull(argumentHandle.get());
    }
    
    @Test
    void Given_CommandWithJoiningParameter_When_ExecuteWithMissingArgument_Then_ReturnsError() {

        when(converterRegistry.getConverter(String.class)).thenReturn(Optional.of(Optional::of));
        class TestCommand {

            @Command("test")
            void command(final @NotNull CommandExecutor executor, 
                         @Join("delimiter") final @NotNull String argument) {

                fail();
            }
        }
        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        final CommandResult result = command.execute(mock(CommandExecutor.class), List.of());

        assertFalse(result.isSuccess());
        assertTrue(result.isError());
    }
    
    @Test
    void Given_CommandWithJoiningParameter_When_ExecuteWithArguments_Then_ExecuteWithArgumentsJoined() {

        final AtomicReference<String> argumentHandle = new AtomicReference<>();
        when(converterRegistry.getConverter(String.class)).thenReturn(Optional.of(Optional::of));
        class TestCommand {

            @Command("test")
            void command(final @NotNull CommandExecutor executor, 
                         @Join("delimiter") final @NotNull String argument) {

                argumentHandle.set(argument);
            }
        }

        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        final CommandResult result = command.execute(mock(CommandExecutor.class), List.of("1", "2"));

        assertTrue(result.isSuccess());
        assertFalse(result.isError());
        assertEquals("1delimiter2", argumentHandle.get());
    }

    @Test
    void Given_NonVoidCommand_When_CreateCommand_Then_Throws() {

        class TestCommand {

            @Command("test")
            @NotNull String command(final @NotNull CommandExecutor executor) {
                return "test";
            }
        }

        assertThrows(IllegalArgumentException.class, () -> createCommand(new TestCommand()));
    }

    @Test
    void Given_CommandWithInvalidExecutor_When_Execute_Then_ReturnsError() {

        class TestCommand {

            @Command("test")
            void command(final @NotNull String executor) {

            }
        }

        assertThrows(IllegalArgumentException.class, () -> createCommand(new TestCommand()));
    }

    @Test
    void Given_ThrowingCommand_When_Execute_Then_ReturnsError() {

        class TestCommand {

            @Command.Default
            void command(final @NotNull CommandExecutor executor) {

                throw new RuntimeException();
            }
        }

        final net.jailgens.enchanted.Command command = createCommand(new TestCommand());

        final CommandResult result = command.execute(mock(CommandExecutor.class), List.of());

        assertTrue(result.isError());
        assertFalse(result.isSuccess());
    }
}
