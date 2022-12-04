package net.jailgens.enchanted;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

final class MaterialConverter implements Converter<@NotNull Material> {

    @Override
    public @NotNull Optional<? extends @NotNull Material> convert(final @NotNull String string) {

        try {
            return Optional.of(Material.valueOf(string));
        } catch (final IllegalArgumentException __) {
            return Optional.empty();
        }
    }
}
