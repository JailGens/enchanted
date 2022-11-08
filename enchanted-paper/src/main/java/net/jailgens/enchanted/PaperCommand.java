package net.jailgens.enchanted;

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

        command.execute(new AdventureCommandExecutor(sender), List.of(args));
        return true;
    }
}
