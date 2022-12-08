package net.jailgens.enchanted;

import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("PatternValidation")
final class PaperSubcommandImpl implements PaperSubcommand {

    private final @NotNull Subcommand subcommand;
    private final @NotNull PaperAdapter paperAdapter;

    @Contract(pure = true)
    PaperSubcommandImpl(final @NotNull Subcommand subcommand,
                        final @NotNull PaperAdapter paperAdapter) {

        Objects.requireNonNull(subcommand, "subcommand cannot be null");
        Objects.requireNonNull(paperAdapter, "paperAdapter cannot be null");

        this.subcommand = subcommand;
        this.paperAdapter = paperAdapter;
    }

    @Override
    @Pattern(NAME_PATTERN)
    public @NotNull String getName() {

        return subcommand.getName();
    }

    @Override
    public @Unmodifiable @NotNull List<String> getAliases() {

        return subcommand.getAliases();
    }

    @Override
    public @Unmodifiable @NotNull List<String> getLabels() {

        return subcommand.getLabels();
    }

    @Override
    @Pattern(DESCRIPTION_PATTERN)
    public @NotNull String getDescription(final @NotNull Locale locale) {

        Objects.requireNonNull(locale, "locale cannot be null");

        return subcommand.getDescription(locale);
    }

    @Override
    @Pattern(DESCRIPTION_PATTERN)
    public @NotNull String getDescription() {

        return subcommand.getDescription();
    }

    @Override
    public @NotNull CommandResult execute(final @NotNull CommandExecutor sender,
                                          final @NotNull List<@NotNull String> arguments) {

        Objects.requireNonNull(sender, "sender cannot be null");
        Objects.requireNonNull(arguments, "arguments cannot be null");

        return subcommand.execute(sender, arguments);
    }

    @Override
    public @NotNull String getUsage() {

        return subcommand.getUsage();
    }

    @Override
    public @NotNull List<@NotNull String> complete(final @NotNull CommandExecutor sender,
                                                   final @NotNull List<@NotNull String> commandArguments) {

        Objects.requireNonNull(sender, "sender cannot be null");
        Objects.requireNonNull(commandArguments, "arguments cannot be null");

        final Arguments arguments = new ListArguments(commandArguments);

        try {
            // TODO(Sparky983): This is a temporary solution. It should be replaced with a more
            //  elegant solution.
            final Optional<Map.Entry<TabCompleter<?>, String>> completer = getParameters().stream()
                    .map((parameter) -> Map.entry(parameter.getTabCompleter(), parameter.parse(arguments)))
                    .filter((entry) -> entry.getValue().isPresent())
                    .map((entry) -> Map.<TabCompleter<?>, String>entry(entry.getKey(), entry.getValue().get()))
                    .reduce((first, second) -> second); // get the last element by only returning the latest element

            if (!arguments.isEmpty()) {
                // The argument is after the final command parameter
                // Example: /feed overlapse THIS_ARGUMENT for /feed <player>
                return List.of();
            }

            return completer.map((entry) -> entry.getKey().complete(entry.getValue()))
                    .orElse(List.of());
        } catch (final ArgumentParseException __) {
            return List.of();
        }
    }

    @Override
    public @Unmodifiable @NotNull List<
            ? extends @NotNull PaperCommandParameter<? extends @NotNull Object>> getParameters() {

        return subcommand.getParameters().stream()
                .map(paperAdapter::adapt)
                .collect(Collectors.toUnmodifiableList());
    }
}
