package net.jailgens.enchanted;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class CommandResultTest {

    @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
    @Test
    void Given_NullMessage_When_CreateResult_Then_Throws() {

        assertThrows(NullPointerException.class, () -> CommandResult.success(null));
    }

    @Test
    void Given_SuccessCommandResult_When_IsSuccess_Then_IsSuccess() {

        final CommandResult success = CommandResult.success("message");

        final boolean isSuccess = success.isSuccess();

        assertTrue(isSuccess);
    }

    @Test
    void Given_SuccessCommandResult_When_IsError_Then_IsNotError() {

        final CommandResult success = CommandResult.success("message");

        final boolean isError = success.isError();

        assertFalse(isError);
    }

    @Test
    void Given_SuccessCommandResult_When_GetMessage_Then_ReturnsMessage() {

        final CommandResult success = CommandResult.success("message");

        final Optional<String> message = success.getMessage();

        assertEquals(Optional.of("message"), message);
    }

    @Test
    void Given_SuccessCommandResult_When_HandleResult_Then_SendsMessage() {

        final CommandExecutor executor = mock(CommandExecutor.class);
        final CommandResult success = CommandResult.success("message");

        success.handleResult(executor);

        verify(executor).sendMessage("message");
        verifyNoMoreInteractions(executor);
    }

    @Test
    void Given_SuccessCommandResultWithoutMessage_When_IsSuccess_Then_IsSuccess() {

        final CommandResult success = CommandResult.success();

        final boolean isSuccess = success.isSuccess();

        assertTrue(isSuccess);
    }

    @Test
    void Given_SuccessCommandResultWithoutMessage_When_IsError_Then_IsNotError() {

        final CommandResult success = CommandResult.success();

        final boolean isError = success.isError();

        assertFalse(isError);
    }

    @Test
    void Given_SuccessCommandResultWithoutMessage_When_GetMessage_Then_ReturnsEmpty() {

        final CommandResult success = CommandResult.success();

        final Optional<String> message = success.getMessage();

        assertEquals(Optional.empty(), message);
    }

    @Test
    void Given_SuccessCommandResultWithoutMessage_When_HandleResultWithNullMessage_Then_DoesNotSend() {

        final CommandExecutor executor = mock(CommandExecutor.class);
        final CommandResult success = CommandResult.success();

        success.handleResult(executor);

        verifyNoInteractions(executor);
    }

    @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
    @Test
    void Given_NullMessage_When_CreateErrorResult_Then_Throws() {

        assertThrows(NullPointerException.class, () -> CommandResult.error(null));
    }

    @Test
    void Given_ErrorCommandResult_When_IsSuccess_Then_IsNotSuccess() {

        final CommandResult error = CommandResult.error("message");

        final boolean isSuccess = error.isSuccess();

        assertFalse(isSuccess);
    }

    @Test
    void Given_ErrorCommandResult_When_IsError_Then_IsError() {

        final CommandResult error = CommandResult.error("message");

        final boolean isError = error.isError();

        assertTrue(isError);
    }

    @Test
    void Given_ErrorCommandResult_When_GetMessage_Then_ReturnsMessage() {

        final CommandResult error = CommandResult.error("message");

        final Optional<String> message = error.getMessage();

        assertEquals(Optional.of("message"), message);
    }

    @Test
    void Given_ErrorCommandResult_When_HandleResult_Then_SendsMessage() {

        final CommandExecutor executor = mock(CommandExecutor.class);
        final CommandResult error = CommandResult.error("message");

        error.handleResult(executor);

        verify(executor).sendMessage("message");
        verifyNoMoreInteractions(executor);
    }
}
