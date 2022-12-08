package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

final class PaperAdapterImpl implements PaperAdapter {

    private final @NotNull TabCompleterRegistry tabCompleterRegistry;

    PaperAdapterImpl(final @NotNull TabCompleterRegistry tabCompleterRegistry) {

        Objects.requireNonNull(tabCompleterRegistry, "tabCompleterRegistry cannot be null");

        this.tabCompleterRegistry = tabCompleterRegistry;
    }

    @Override
    public @NotNull PaperCommand adapt(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        return new PaperCommandImpl(command);
    }

    @Override
    public @NotNull PaperSubcommand adapt(final @NotNull Subcommand subcommand) {

        Objects.requireNonNull(subcommand, "command cannot be null");

        return new PaperSubcommandImpl(subcommand, this);
    }

    @Override
    public @NotNull PaperCommandGroup adapt(final @NotNull CommandGroup group) {

        Objects.requireNonNull(group, "group cannot be null");

        return new PaperCommandGroupImpl(group, adapt((Subcommand) group), this);
    }

    @Override
    public @NotNull <T extends @NotNull Object> PaperCommandParameter<@NotNull T> adapt(
            final @NotNull CommandParameter<@NotNull T> parameter) {

        Objects.requireNonNull(parameter, "parameter cannot be null");

        return new PaperCommandParameterImpl<>(parameter,
                tabCompleterRegistry.getTabCompleter(parameter.getType()));
    }
}
