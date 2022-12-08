package net.jailgens.enchanted;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("PatternValidation")
final class PaperCommandGroupImpl implements PaperCommandGroup {

    private final @NotNull CommandGroup commandGroup;
    private final @NotNull PaperInspectable defaultCommandParameters;
    private final @NotNull CommandMap<@NotNull PaperSubcommand> subcommands;

    /**
     * Constructs a new {@link PaperCommandGroupImpl}.
     *
     * @param commandGroup the command group.
     * @param defaultCommandParameters an inspectable that contains the default command parameters.
     * @param adapter the paper adapter.
     * @throws NullPointerException if the command group, the default command parameters or the
     * adapter is {@code null}.
     */
    @Contract(pure = true)
    PaperCommandGroupImpl(final @NotNull CommandGroup commandGroup,
                          final @NotNull PaperInspectable defaultCommandParameters,
                          final @NotNull PaperAdapter adapter) {

        Objects.requireNonNull(commandGroup, "commandGroup cannot be null");
        Objects.requireNonNull(defaultCommandParameters, "defaultCommandParameters cannot be null");
        Objects.requireNonNull(adapter, "adapter cannot be null");

        this.commandGroup = commandGroup;
        this.defaultCommandParameters = defaultCommandParameters;
        this.subcommands = CommandMap.create();
        commandGroup.getSubcommands().stream()
                .map((command) -> {
                    if (command instanceof CommandGroup) {
                        return adapter.adapt((CommandGroup) command);
                    }
                    return adapter.adapt(command);
                })
                .forEach(subcommands::registerCommand);
    }

    @Override
    @Pattern(NAME_PATTERN)
    public @NotNull String getName() {

        return commandGroup.getName();
    }

    @Override
    public @NotNull @Unmodifiable List<@NotNull String> getAliases() {

        return commandGroup.getAliases();
    }

    @Override
    public @NotNull @Unmodifiable List<@NotNull String> getLabels() {

        return commandGroup.getLabels();
    }

    @Override
    @Pattern(DESCRIPTION_PATTERN)
    public @NotNull String getDescription(final @NotNull Locale locale) {

        Objects.requireNonNull(locale, "locale cannot be null");

        return commandGroup.getDescription(locale);
    }

    @Override
    @Pattern(DESCRIPTION_PATTERN)
    public @NotNull String getDescription() {

        return commandGroup.getDescription();
    }

    @Override
    public @NotNull @Unmodifiable List<? extends @NotNull PaperCommandParameter<?>> getParameters() {

        return defaultCommandParameters.getParameters();
    }

    @Override
    public @NotNull Optional<? extends @NotNull PaperSubcommand> getCommand(
            final @NotNull String label) {

        Objects.requireNonNull(label, "label cannot be null");

        return subcommands.getCommand(label);
    }

    @Override
    public @NotNull @Unmodifiable Collection<? extends @NotNull PaperSubcommand> getSubcommands() {

        return subcommands.getRegisteredCommands();
    }

    @Override
    public @NotNull CommandResult execute(final @NotNull CommandExecutor sender,
                                          final @NotNull List<@NotNull String> arguments) {

        Objects.requireNonNull(sender, "sender cannot be null");
        Objects.requireNonNull(arguments, "arguments cannot be null");

        return commandGroup.execute(sender, arguments);
    }

    @Override
    public @NotNull String getUsage() {

        return commandGroup.getUsage();
    }

    @Override
    public @NotNull List<@NotNull String> complete(final @NotNull CommandExecutor sender,
                                                   final @NotNull List<@NotNull String> arguments) {

        Objects.requireNonNull(sender, "sender cannot be null");
        Objects.requireNonNull(arguments, "arguments cannot be null");

        if (arguments.size() <= 1) {
            return getSubcommands()
                    .stream()
                    .map(Command::getName)
                    .collect(Collectors.toCollection(() -> new ArrayList<>(defaultCommandParameters.complete(sender, arguments))));
        }

        return getCommand(arguments.get(0))
                .map((subcommand) ->
                        subcommand.complete(sender, arguments.subList(1, arguments.size()))
                ).orElse(defaultCommandParameters.complete(sender, arguments));
    }
}
