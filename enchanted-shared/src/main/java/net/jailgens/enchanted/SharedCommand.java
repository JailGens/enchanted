package net.jailgens.enchanted;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("PatternValidation")
final class SharedCommand implements Command {

    private final Executable executable;
    private final CommandInfo commandInfo;

    SharedCommand(final @NotNull Executable executable, final @NotNull CommandInfo commandInfo) {

        Objects.requireNonNull(executable, "executable cannot be null");
        Objects.requireNonNull(commandInfo, "commandInfo cannot be null");

        this.executable = executable;
        this.commandInfo = commandInfo;
    }

    @Override
    public @NotNull CommandResult execute(final @NotNull CommandExecutor sender,
                                          final @NotNull List<@NotNull String> arguments) {

        Objects.requireNonNull(sender, "sender cannot be null");
        Objects.requireNonNull(arguments, "arguments cannot be null");

        return executable.execute(sender, arguments);
    }

    @Override
    public @NotNull String getUsage() {

        return executable.getUsage();
    }

    @Pattern(NAME_PATTERN)
    @Override
    public @NotNull String getName() {

        return commandInfo.getName();
    }

    @Override
    public @Unmodifiable @NotNull List<String> getAliases() {

        return commandInfo.getAliases();
    }

    @Override
    public @Unmodifiable @NotNull List<String> getLabels() {

        return commandInfo.getLabels();
    }

    @Pattern(DESCRIPTION_PATTERN)
    @Override
    public @NotNull String getDescription(final @NotNull Locale locale) {

        Objects.requireNonNull(locale, "locale cannot be null");

        return getDescription();
    }

    @Pattern(DESCRIPTION_PATTERN)
    @Override
    public @NotNull String getDescription() {

        return commandInfo.getDescription();
    }
}
