package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;
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
    public abstract boolean equals(final @Nullable Object o);

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

    private abstract static class AbstractTranslateCommandResult
            implements CommandResult.Translate {

        private final @NotNull String key;
        private final @NotNull Map<@NotNull String, @NotNull Object> placeholders;

        @Contract(pure = true)
        protected AbstractTranslateCommandResult(
                final @NotNull String key,
                final @NotNull Map<@NotNull String, @NotNull Object> placeholders) {

            Objects.requireNonNull(key, "key cannot be null");
            Objects.requireNonNull(placeholders, "placeholders cannot be null");

            this.key = key;
            this.placeholders = Map.copyOf(placeholders);
        }

        @Override
        public @NotNull Optional<@NotNull String> getMessage() {

            return Optional.empty();
        }

        @Override
        public void handleResult(final @NotNull CommandExecutor executor) {

            executor.sendTranslatedMessage(key, placeholders);
        }

        @Override
        public @NotNull String getKey() {

            return key;
        }

        @Override
        public @NotNull @Unmodifiable Map<@NotNull String, @NotNull Object> getPlaceholders() {

            return placeholders;
        }

        @Override
        public abstract boolean equals(final @Nullable Object o);

        @Override
        public int hashCode() {

            return Objects.hash(key, placeholders);
        }
    }

    static final class TranslateSuccessImpl extends AbstractTranslateCommandResult
            implements Success {

        TranslateSuccessImpl(final @NotNull String key,
                             final @NotNull Map<@NotNull String, @NotNull Object> placeholders) {

            super(key, placeholders);
        }

        @Override
        public boolean equals(final @Nullable Object o) {

            if (this == o) {
                return true;
            }

            if (!(o instanceof Success) || !(o instanceof Translate)) {
                return false;
            }

            final Translate translate = (Translate) o;

            return translate.getMessage().equals(getMessage())
                    && getKey().equals(translate.getKey())
                    && getPlaceholders().equals(translate.getPlaceholders());
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

    static final class TranslateErrorImpl extends AbstractTranslateCommandResult
            implements Error {

        TranslateErrorImpl(final @NotNull String key,
                           final @NotNull Map<@NotNull String, @NotNull Object> placeholders) {

            super(key, placeholders);
        }

        @Override
        public boolean equals(final @Nullable Object o) {

            if (this == o) {
                return true;
            }

            if (!(o instanceof Error) || !(o instanceof Translate)) {
                return false;
            }

            final Translate translate = (Translate) o;

            return translate.getMessage().equals(getMessage())
                    && getKey().equals(translate.getKey())
                    && getPlaceholders().equals(translate.getPlaceholders());
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
