package net.jailgens.enchanted;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

final class PaperCommand extends org.bukkit.command.Command {

    private final Command command;

    PaperCommand(final @NotNull Command command) {

        super(command.getName()); // implicit null check

        setDescription(command.getDescription());
        setAliases(command.getAliases());

        this.command = command;
    }

    @Override
    public boolean execute(final @NotNull CommandSender sender,
                           final @NotNull String commandLabel,
                           final @NotNull String @NotNull [] args) {

        final CommandResult result = command.execute(new PaperCommandExecutor(sender), List.of(args));

        if (result.isError()) {
            result.getMessage()
                    .ifPresent((message) -> sender.sendMessage(Component.text(message, NamedTextColor.RED)));
        } else {
            assert result.isSuccess();
            result.getMessage()
                    .ifPresent((message) -> sender.sendMessage(Component.text(message)));
        }

        return true;
    }
}
