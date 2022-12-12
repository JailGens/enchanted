package net.jailgens.enchanted;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("PatternValidation")
final class PaperCommandParameterImpl<T extends @NotNull Object> implements
        PaperCommandParameter<@NotNull T> {

    private final @NotNull CommandParameter<@NotNull T> commandParameter;
    private final @NotNull TabCompleter<@NotNull T> tabCompleter;

    PaperCommandParameterImpl(final @NotNull CommandParameter<@NotNull T> commandParameter,
                              final @NotNull TabCompleter<@NotNull T> tabCompleter) {

        Objects.requireNonNull(commandParameter, "commandParameter cannot be null");
        Objects.requireNonNull(tabCompleter, "tabCompleter cannot be null");

        this.commandParameter = commandParameter;
        this.tabCompleter = tabCompleter;
    }

    @Override
    @Pattern(NAME_PATTERN)
    public @NotNull String getName() {

        return commandParameter.getName();
    }

    @Override
    public boolean isOptional() {

        return commandParameter.isOptional();
    }

    @Override
    public @NotNull Optional<@NotNull String> getDelimiter() {

        return commandParameter.getDelimiter();
    }

    @Override
    public @NotNull Converter<@NotNull T> getConverter() {

        return commandParameter.getConverter();
    }

    @Override
    public @NotNull Optional<@NotNull String> parse(final @NotNull Arguments arguments) {

        Objects.requireNonNull(arguments, "arguments cannot be null");

        return commandParameter.parse(arguments);
    }

    @Override
    public @Nullable T resolve(final @NotNull Arguments arguments) {

        Objects.requireNonNull(arguments, "arguments cannot be null");

        return commandParameter.resolve(arguments);
    }

    @Override
    public @NotNull Class<@NotNull T> getType() {

        return commandParameter.getType();
    }

    @Override
    public @NotNull TabCompleter<@NotNull T> getTabCompleter() {

        return tabCompleter;
    }
}
