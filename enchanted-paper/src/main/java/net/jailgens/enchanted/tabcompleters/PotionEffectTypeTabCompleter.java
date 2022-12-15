package net.jailgens.enchanted.tabcompleters;

import net.jailgens.enchanted.TabCompleter;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class PotionEffectTypeTabCompleter implements TabCompleter<@NotNull PotionEffectType> {

    private static final @NotNull List<@NotNull String> POTION_EFFECT_TYPE_COMPLETIONS =
            Arrays.stream(PotionEffectType.values())
                    .map(PotionEffectType::getName)
                    .collect(Collectors.toUnmodifiableList());

    @Override
    public @NotNull List<@NotNull String> complete(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        return POTION_EFFECT_TYPE_COMPLETIONS;
    }
}
