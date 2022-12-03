package net.jailgens.enchanted;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * The paper implementation of {@link CommandManager}.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface PaperCommandManager extends CommandManager {

    /**
     * Creates a new command manager.
     *
     * @param plugin the plugin.
     * @return the command manager.
     * @throws NullPointerException if the plugin is {@code null}.
     * @since 0.0.0
     */
    static @NotNull PaperCommandManager create(final @NotNull Plugin plugin) {

        return new PaperCommandManagerImpl(plugin);
    }
}
