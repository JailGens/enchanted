package net.jailgens.enchanted;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class PaperCommandExecutor implements CommandExecutor, Audience {

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
    public void sendMessage(final @NonNull ComponentLike message) {

        sender.sendMessage(message);
    }

    @Override
    public void sendMessage(final @NonNull Identified source,
                            final @NonNull ComponentLike message) {

        sender.sendMessage(source, message);
    }

    @Override
    public void sendMessage(final @NonNull Identity source,
                            final @NonNull ComponentLike message) {

        sender.sendMessage(source, message);
    }

    @Override
    public void sendMessage(final @NonNull Component message) {

        sender.sendMessage(message);
    }

    @Override
    public void sendMessage(final @NonNull Identified source, final @NonNull Component message) {

        sender.sendMessage(source, message);
    }

    @Override
    public void sendMessage(final @NonNull Identity source, final @NonNull Component message) {

        sender.sendMessage(source, message);
    }

    @Override
    public void sendMessage(final @NonNull ComponentLike message, final @NonNull MessageType type) {

        sender.sendMessage(message, type);
    }

    @Override
    public void sendMessage(final @NonNull Identified source,
                            final @NonNull ComponentLike message,
                            final @NonNull MessageType type) {

        sender.sendMessage(source, message, type);
    }

    @Override
    public void sendMessage(final @NonNull Identity source,
                            final @NonNull ComponentLike message,
                            final @NonNull MessageType type) {

        sender.sendMessage(source, message, type);
    }

    @Override
    public void sendMessage(final @NonNull Component message, final @NonNull MessageType type) {

        sender.sendMessage(message, type);
    }

    @Override
    public void sendMessage(final @NonNull Identified source,
                            final @NonNull Component message,
                            final @NonNull MessageType type) {

        sender.sendMessage(source, message, type);
    }

    @Override
    public void sendMessage(final @NonNull Identity source,
                            final @NonNull Component message,
                            final @NonNull MessageType type) {

        sender.sendMessage(source, message, type);
    }

    @Override
    public void sendActionBar(final @NonNull ComponentLike message) {

        sender.sendActionBar(message);
    }

    @Override
    public void sendActionBar(final @NonNull Component message) {

        sender.sendActionBar(message);
    }

    @Override
    public void sendPlayerListHeader(final @NonNull ComponentLike header) {

        sender.sendPlayerListHeader(header);
    }

    @Override
    public void sendPlayerListHeader(final @NonNull Component header) {

        sender.sendPlayerListHeader(header);
    }

    @Override
    public void sendPlayerListFooter(final @NonNull ComponentLike footer) {

        sender.sendPlayerListFooter(footer);
    }

    @Override
    public void sendPlayerListFooter(final @NonNull Component footer) {

        sender.sendPlayerListFooter(footer);
    }

    @Override
    public void sendPlayerListHeaderAndFooter(final @NonNull ComponentLike header,
                                              final @NonNull ComponentLike footer) {

        sender.sendPlayerListHeaderAndFooter(header, footer);
    }

    @Override
    public void sendPlayerListHeaderAndFooter(final @NonNull Component header,
                                              final @NonNull Component footer) {

        sender.sendPlayerListHeaderAndFooter(header, footer);
    }

    @Override
    public void showTitle(final @NonNull Title title) {

        sender.showTitle(title);
    }

    @Override
    public void clearTitle() {

        sender.clearTitle();
    }

    @Override
    public void resetTitle() {

        sender.resetTitle();
    }

    @Override
    public void showBossBar(final @NonNull BossBar bar) {

        sender.showBossBar(bar);
    }

    @Override
    public void hideBossBar(final @NonNull BossBar bar) {

        sender.hideBossBar(bar);
    }

    @Override
    public void playSound(final @NonNull Sound sound) {

        sender.playSound(sound);
    }

    @Override
    public void playSound(final @NonNull Sound sound,
                          final double x,
                          final double y,
                          final double z) {

        sender.playSound(sound, x, y, z);
    }

    @Override
    public void stopSound(final @NonNull SoundStop stop) {

        sender.stopSound(stop);
    }

    @Override
    public void openBook(final Book.@NonNull Builder book) {

        sender.openBook(book);
    }

    @Override
    public void openBook(final @NonNull Book book) {

        sender.openBook(book);
    }
}
