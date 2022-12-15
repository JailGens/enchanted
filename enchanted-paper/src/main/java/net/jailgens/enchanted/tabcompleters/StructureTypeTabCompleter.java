package net.jailgens.enchanted.tabcompleters;

import net.jailgens.enchanted.TabCompleter;
import org.bukkit.StructureType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public final class StructureTypeTabCompleter implements TabCompleter<@NotNull StructureType> {

    private static final @NotNull List<@NotNull String> STRUCTURE_TYPE_COMPLETIONS =
            StructureType.getStructureTypes()
                    .keySet()
                    .stream()
                    .collect(Collectors.toUnmodifiableList());

    @Override
    public @NotNull List<@NotNull String> complete(final @NotNull String string) {

        return STRUCTURE_TYPE_COMPLETIONS;
    }
}
