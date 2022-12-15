package net.jailgens.enchanted.tabcompleters;

import net.jailgens.enchanted.TabCompleter;
import net.kyori.adventure.key.Key;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: Keyed enum tab completer
public final class SoundTabCompleter implements TabCompleter<@NotNull Sound> {

    private static final List<String> SOUND_COMPLETIONS = Arrays.stream(Sound.values())
            .map(Sound::key)
            .map(Key::asString)
            .collect(Collectors.toUnmodifiableList());

    @Override
    public @NotNull List<@NotNull String> complete(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        return SOUND_COMPLETIONS;
    }
}
