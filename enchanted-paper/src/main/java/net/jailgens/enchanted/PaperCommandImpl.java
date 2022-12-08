package net.jailgens.enchanted;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@SuppressWarnings({"PatternOverriddenByNonAnnotatedMethod", "PatternValidation"})
final class PaperCommandImpl implements PaperCommand {

    private final Command command;

    @Contract(pure = true)
    PaperCommandImpl(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        this.command = command;
    }

    @Override
    public @NotNull String getName() {

        return command.getName();
    }

    @Override
    public @Unmodifiable @NotNull List<String> getAliases() {

        return command.getAliases();
    }

    @Override
    public @Unmodifiable @NotNull List<String> getLabels() {

        return command.getLabels();
    }

    @Override
    public @NotNull String getDescription(final @NotNull Locale locale) {

        Objects.requireNonNull(locale, "locale cannot be null");

        return command.getDescription(locale);
    }

    @Override
    public @NotNull String getDescription() {

        return command.getDescription();
    }

    @Override
    public @NotNull CommandResult execute(final @NotNull CommandExecutor sender,
                                          final @NotNull List<@NotNull String> arguments) {

        Objects.requireNonNull(sender, "sender cannot be null");
        Objects.requireNonNull(arguments, "arguments cannot be null");

        return command.execute(sender, arguments);
    }

    @Override
    public @NotNull String getUsage() {

        return command.getUsage();
    }

    @Override
    public @NotNull List<@NotNull String> complete(final @NotNull CommandExecutor sender,
                                                   final @NotNull List<@NotNull String> arguments) {

        Objects.requireNonNull(sender, "sender cannot be null");

        return List.of();
    }
}
