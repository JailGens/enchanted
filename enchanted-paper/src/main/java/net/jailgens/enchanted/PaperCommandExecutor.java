package net.jailgens.enchanted;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

final class PaperCommandExecutor implements CommandExecutor {

    private final @NotNull CommandSender sender;
    private final @NotNull PaperTranslationRegistry translationRegistry;

    @Contract(pure = true)
    PaperCommandExecutor(final @NotNull CommandSender sender,
                         final @NotNull PaperTranslationRegistry translationRegistry) {


        Objects.requireNonNull(sender, "sender cannot be null");

        this.sender = sender;
        this.translationRegistry = translationRegistry;
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
    public void sendTranslatedMessage(final @NotNull String key,
                                      final @NotNull Map<@NotNull String, @NotNull Object> placeholders) {

        Objects.requireNonNull(key, "key cannot be null");
        Objects.requireNonNull(placeholders, "placeholders cannot be null");

        sender.sendMessage(translationRegistry.getTranslation(key, placeholders));
    }

    @Override
    public CommandSender getAlternativeExecutor() {

        return sender;
    }
}
