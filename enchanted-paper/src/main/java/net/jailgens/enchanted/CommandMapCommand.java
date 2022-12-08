package net.jailgens.enchanted;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An adapted {@link Command} for the {@link org.bukkit.command.CommandMap}.
 *
 * @author Sparky983
 */
final class CommandMapCommand extends org.bukkit.command.Command {

    private final @NotNull PaperCommand command;

    @Contract(pure = true)
    CommandMapCommand(final @NotNull PaperCommand command) {

        super(command.getName()); // implicit null check

        setDescription(command.getDescription());
        setAliases(command.getAliases());

        this.command = command;
    }

    @Override
    public boolean execute(final @NotNull CommandSender sender,
                           final @NotNull String commandLabel,
                           final @NotNull String @NotNull [] args) {

        final CommandExecutor executor = new PaperCommandExecutor(sender);
        final CommandResult result = command.execute(executor, List.of(args));
        result.handleResult(executor);

        return true;
    }

    @Override
    public @NotNull List<@NotNull String> tabComplete(final @NotNull CommandSender sender,
                                                      final @NotNull String alias,
                                                      final @NotNull String[] args) {

        return command.complete(new PaperCommandExecutor(sender), List.of(args)).stream()
                .map((completion) -> {
                    if (completion.lastIndexOf(' ') != -1) {
                        return completion.substring(completion.lastIndexOf(' ') + 1);
                    }
                    return completion;
                })
                .filter((completion) -> completion.startsWith(args[args.length - 1]))
                .collect(Collectors.toList());
    }
}
