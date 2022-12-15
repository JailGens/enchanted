package net.jailgens.enchanted.converters;

import net.jailgens.enchanted.Converter;
import net.kyori.adventure.key.Key;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public final class SoundConverter implements Converter<@NotNull Sound> {

    private static final @NotNull HashMap<@NotNull Key, @NotNull Sound> SOUND_MAP = new HashMap<>();

    static {
        for (final Sound sound : Sound.values()) {
            SOUND_MAP.put(sound.key(), sound);
        }
    }

    private final @NotNull Converter<? extends @NotNull Key> keyConverter;

    public SoundConverter(final @NotNull Converter<? extends @NotNull Key> keyConverter) {

        Objects.requireNonNull(keyConverter, "keyConverter cannot be null");

        this.keyConverter = keyConverter;
    }

    @Override
    public @NotNull Optional<? extends @NotNull Sound> convert(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        return keyConverter.convert(string)
                .flatMap((key) -> Optional.ofNullable(SOUND_MAP.get(key)));
    }
}
