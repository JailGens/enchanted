package net.jailgens.enchanted.tabcompleters;

import net.jailgens.enchanted.TabCompleter;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class MaterialTabCompleter implements TabCompleter<@NotNull Material> {

    private static final @NotNull List<@NotNull String> MATERIAL_COMPLETIONS =
            Arrays.stream(Material.values())
                    .map(Material::getKey)
                    .map(Key::asString)
                    .collect(Collectors.toUnmodifiableList());

    @Override
    public @NotNull List<@NotNull String> complete(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        return MATERIAL_COMPLETIONS;
    }
}
