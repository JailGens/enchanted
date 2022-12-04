package net.jailgens.enchanted;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

final class MaterialConverter implements Converter<@NotNull Material> {

    @Override
    public @NotNull Optional<? extends @NotNull Material> convert(final @NotNull String string) {

        Objects.requireNonNull(string, "string cannot be null");

        final Material material = Material.matchMaterial(string);

        if (material == null) {
            throw new ArgumentParseException("Material \"" + string + "\" not found");
        }

        return Optional.of(material);
    }
}
