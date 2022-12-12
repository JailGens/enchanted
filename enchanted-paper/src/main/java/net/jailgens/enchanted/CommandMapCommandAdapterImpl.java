package net.jailgens.enchanted;

import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

final class CommandMapCommandAdapterImpl implements CommandMapCommandAdapter {

    @Override
    public @NotNull Command adapt(final @NotNull PaperCommand command) {

        Objects.requireNonNull(command, "command cannot be null");

        return new CommandMapCommand(command);
    }
}
