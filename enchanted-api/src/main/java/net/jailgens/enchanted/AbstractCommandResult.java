package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

abstract class AbstractCommandResult implements CommandResult {

    private final @Nullable String message;

    protected AbstractCommandResult(final @Nullable String message) {

        this.message = message;
    }

    @Override
    public @NotNull Optional<@NotNull String> getMessage() {

        return Optional.ofNullable(message);
    }

    @Override
    public void handleResult(final @NotNull CommandExecutor executor) {

        if (message == null) {
            return;
        }
        executor.sendMessage(message);
    }

    static final class SuccessImpl extends AbstractCommandResult implements CommandResult.Success {

        static final SuccessImpl SUCCESS = new SuccessImpl(null);

        SuccessImpl(final @Nullable String message) {

            super(message);
        }

        @Override
        public boolean isSuccess() {

            return true;
        }

        @Override
        public boolean isError() {

            return false;
        }
    }

    static final class ErrorImpl extends AbstractCommandResult implements CommandResult.Error {

        ErrorImpl(final @Nullable String message) {

            super(message);
        }

        @Override
        public boolean isSuccess() {

            return false;
        }

        @Override
        public boolean isError() {

            return true;
        }
    }
}
