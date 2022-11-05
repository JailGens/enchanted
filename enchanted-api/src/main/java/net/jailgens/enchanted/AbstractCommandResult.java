package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
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

    @Override
    public abstract boolean equals(final Object o);

    @Override
    public int hashCode() {

        return Objects.hashCode(message);
    }

    static final class SuccessImpl extends AbstractCommandResult implements Success {

        static final Success SUCCESS = new SuccessImpl(null);

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

        @Override
        public boolean equals(final @Nullable Object o) {

            if (this == o) {
                return true;
            }
            if (!(o instanceof CommandResult.Success)) {
                return false;
            }
            final Success success = (Success) o;
            return success.getMessage().equals(getMessage());
        }
    }

    static final class ErrorImpl extends AbstractCommandResult implements Error {

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

        @Override
        public boolean equals(final @Nullable Object o) {

            if (this == o) {
                return true;
            }
            if (!(o instanceof CommandResult.Error)) {
                return false;
            }
            final Error error = (Error) o;
            return error.getMessage().equals(getMessage());
        }
    }
}
