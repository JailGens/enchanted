package net.jailgens.enchanted;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

final class PaperCommandExecutor implements CommandExecutor {

    private final @NotNull CommandSender sender;

    @Contract(pure = true)
    PaperCommandExecutor(final @NotNull CommandSender sender) {

        Objects.requireNonNull(sender, "sender cannot be null");

        this.sender = sender;
    }

    public @NotNull String getName() {

        return sender.getName();
    }

    @Override
    public void sendMessage(final @NotNull String message) {

        Objects.requireNonNull(message, "message cannot be null");

        sender.sendMessage(Component.text(message));
    }

    @Override
    public CommandSender getAlternativeExecutor() {

        return sender;
    }
}
