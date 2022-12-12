package net.jailgens.enchanted;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

final class TabCompleterRegistryImpl implements TabCompleterRegistry {

    private final @NotNull Map<@NotNull Class<? extends @NotNull Object>, @NotNull TabCompleter<? extends @NotNull Object>> tabCompleters = new HashMap<>();

    @Contract(pure = true)
    TabCompleterRegistryImpl(final @NotNull Server server) {

        Objects.requireNonNull(server, "server cannot be null");

        registerTabCompleter(Player.class, new PlayerTabCompleter(server));
    }

    @Override
    public <T extends @NotNull Object> void registerTabCompleter(
            final @NotNull Class<@NotNull T> type,
            final @NotNull TabCompleter<@NotNull T> completer) {

        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(completer, "completer cannot be null");

        tabCompleters.put(type, completer);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends @NotNull Object> @NotNull TabCompleter<@NotNull T> getTabCompleter(
            final @NotNull Class<@NotNull T> type) {

        Objects.requireNonNull(type, "type cannot be null");

        final TabCompleter<T> tabCompleter = (TabCompleter<T>) tabCompleters.get(type);

        // purposely not using getOrDefault to avoid unnecessary allocation
        if (tabCompleter == null) {
            return TabCompleter.empty();
        }

        return tabCompleter;
    }
}
