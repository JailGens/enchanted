package net.jailgens.enchanted.converters;

import net.jailgens.enchanted.Converter;
import org.bukkit.GameMode;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class GameModeConverter implements Converter<@NotNull GameMode> {

    /**
     * Hard code it because later on, if more game modes get added there'll be conflicts.
     */
    private static final @NotNull Map<@NotNull String, @NotNull GameMode> GAME_MODES = Map.ofEntries(
            Map.entry("0", GameMode.SURVIVAL),
            Map.entry("survival", GameMode.SURVIVAL),
            Map.entry("s", GameMode.SURVIVAL),
            Map.entry("1", GameMode.CREATIVE),
            Map.entry("creative", GameMode.CREATIVE),
            Map.entry("c", GameMode.CREATIVE),
            Map.entry("2", GameMode.ADVENTURE),
            Map.entry("adventure", GameMode.ADVENTURE),
            Map.entry("a", GameMode.ADVENTURE),
            Map.entry("3", GameMode.SPECTATOR),
            Map.entry("spectator", GameMode.SPECTATOR),
            Map.entry("sp", GameMode.SPECTATOR)
    );

    @Override
    public @NotNull Optional<? extends @NotNull GameMode> convert(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        return Optional.ofNullable(GAME_MODES.get(string.toLowerCase()));
    }
}
