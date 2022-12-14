package net.jailgens.enchanted.tabcompleters;

import net.jailgens.enchanted.TabCompleter;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class PlayerTabCompleter implements TabCompleter<@NotNull Player> {

    private final @NotNull Server server;

    @Contract(pure = true)
    public PlayerTabCompleter(final @NotNull Server server) {

        Objects.requireNonNull(server, "server cannot be null");

        this.server = server;
    }

    @Override
    public @NotNull List<@NotNull String> complete(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        return server.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toUnmodifiableList());
    }
}
