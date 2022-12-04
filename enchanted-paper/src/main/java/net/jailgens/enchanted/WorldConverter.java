package net.jailgens.enchanted;

import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

final class WorldConverter implements Converter<@NotNull World> {

    private final @NotNull Server server;

    @Contract(pure = true)
    WorldConverter(final @NotNull Server server) {

        Objects.requireNonNull(server, "server cannot be null");

        this.server = server;
    }

    @Override
    public @NotNull Optional<? extends @NotNull World> convert(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        final World worldByName = server.getWorld(string);

        if (worldByName != null) {
            return Optional.of(worldByName);
        }

        try {
            final UUID uuid = UUID.fromString(string);
            final World worldByUUID = server.getWorld(uuid);

            if (worldByUUID != null) {
                return Optional.of(worldByUUID);
            }
        } catch (final IllegalArgumentException __) {
            // try by namespaced key
        }

        final NamespacedKey key = NamespacedKey.fromString(string);

        if (key == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(server.getWorld(key));
    }
}
