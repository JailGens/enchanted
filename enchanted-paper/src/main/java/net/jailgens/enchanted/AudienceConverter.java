package net.jailgens.enchanted;

import net.kyori.adventure.audience.Audience;
import org.bukkit.Server;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

final class AudienceConverter implements Converter<@NotNull Audience> {

    private final @NotNull Server server;

    @Contract(pure = true)
    AudienceConverter(final @NotNull Server server) {

        Objects.requireNonNull(server, "server cannot be null");

        this.server = server;
    }


    @Override
    public @NotNull Optional<? extends @NotNull Audience> convert(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        if (string.equals("*")) {
            return Optional.of(server);
        }

        if (string.equalsIgnoreCase("console")) {
            return Optional.of(server.getConsoleSender());
        }

        final Audience audience = server.getPlayerExact(string);

        if (audience == null) {
            throw new ArgumentParseException("Player \"" + string + "\" not found");
        }

        return Optional.of(audience);
    }
}
