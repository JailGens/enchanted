package net.jailgens.enchanted.converters;

import net.jailgens.enchanted.Converter;
import org.bukkit.Difficulty;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class DifficultyConverter implements Converter<@NotNull Difficulty> {

    /**
     * Hard code it because later on, if more difficulties get added there'll be conflicts.
     */
    private static final @NotNull Map<@NotNull String, @NotNull Difficulty> DIFFICULTIES = Map.ofEntries(
            Map.entry("0", Difficulty.PEACEFUL),
            Map.entry("peaceful", Difficulty.PEACEFUL),
            Map.entry("p", Difficulty.PEACEFUL),
            Map.entry("1", Difficulty.EASY),
            Map.entry("easy", Difficulty.EASY),
            Map.entry("e", Difficulty.EASY),
            Map.entry("2", Difficulty.NORMAL),
            Map.entry("normal", Difficulty.NORMAL),
            Map.entry("n", Difficulty.NORMAL),
            Map.entry("3", Difficulty.HARD),
            Map.entry("hard", Difficulty.HARD),
            Map.entry("h", Difficulty.HARD)
    );

    @Override
    public @NotNull Optional<? extends @NotNull Difficulty> convert(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        return Optional.ofNullable(DIFFICULTIES.get(string.toLowerCase()));
    }
}
