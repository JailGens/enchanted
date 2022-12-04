package net.jailgens.enchanted;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

final class PlayerConverter implements Converter<@NotNull Player> {

    private final @NotNull Server server;

    @Contract(pure = true)
    PlayerConverter(final @NotNull Server server) {

        Objects.requireNonNull(server, "server cannot be null");

        this.server = server;
    }

    @Override
    public @NotNull Optional<? extends @NotNull Player> convert(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        final Player player = server.getPlayerExact(string);

        if (player == null) {
            throw new ArgumentParseException("Player \"" + string + "\" not found");
        }

        return Optional.of(player);
    }
}
