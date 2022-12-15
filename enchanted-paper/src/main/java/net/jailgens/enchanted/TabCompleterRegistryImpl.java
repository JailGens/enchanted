package net.jailgens.enchanted;

import com.destroystokyo.paper.ClientOption;
import com.destroystokyo.paper.block.TargetBlockInfo;
import com.destroystokyo.paper.entity.ai.GoalType;
import io.papermc.paper.datapack.Datapack;
import io.papermc.paper.enchantments.EnchantmentRarity;
import io.papermc.paper.inventory.ItemRarity;
import io.papermc.paper.world.MoonPhase;
import net.jailgens.enchanted.tabcompleters.EnumTabCompleter;
import net.jailgens.enchanted.tabcompleters.MaterialTabCompleter;
import net.jailgens.enchanted.tabcompleters.PotionEffectTypeTabCompleter;
import net.jailgens.enchanted.tabcompleters.SoundTabCompleter;
import net.jailgens.enchanted.tabcompleters.StructureTypeTabCompleter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.MainHand;
import org.bukkit.loot.LootTables;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: Change lists to be modifiable for performance reasons
final class TabCompleterRegistryImpl implements TabCompleterRegistry {

    private final @NotNull Map<@NotNull Class<? extends @NotNull Object>, @NotNull TabCompleter<? extends @NotNull Object>> tabCompleters = new HashMap<>();

    @Contract(pure = true)
    TabCompleterRegistryImpl(final @NotNull Server server) {

        Objects.requireNonNull(server, "server cannot be null");

        registerTabCompleters(server);
    }

    private void registerTabCompleters(final @NotNull Server server) {

        assert server != null;

        registerEnumTabCompleter(Art.class);
        registerEnumTabCompleter(ChatColor.class);
        registerEnumTabCompleter(CoalType.class);
        registerEnumTabCompleter(CropState.class);
        registerEnumTabCompleter(Difficulty.class);
        registerEnumTabCompleter(Effect.class);
        registerEnumTabCompleter(Fluid.class);
        registerEnumTabCompleter(FireworkEffect.Type.class);
        // registerEnumTabCompleter(org.bukkit.GameEvent.class); TODO: 1.17
        registerEnumTabCompleter(GameMode.class);
        registerEnumTabCompleter(Instrument.class);
        registerEnumTabCompleter(Note.Tone.class);
        registerEnumTabCompleter(Particle.class);
        registerEnumTabCompleter(PortalType.class);
        registerEnumTabCompleter(SoundCategory.class);
        registerEnumTabCompleter(Statistic.class);
        registerEnumTabCompleter(Statistic.Type.class);
        registerEnumTabCompleter(GameMode.class);

        registerEnumTabCompleter(TreeSpecies.class);
        registerEnumTabCompleter(WeatherType.class);
        registerEnumTabCompleter(World.Environment.class);
        registerEnumTabCompleter(WorldType.class);
        registerEnumTabCompleter(PotionType.class);
        registerEnumTabCompleter(PermissionDefault.class);
        registerEnumTabCompleter(LootTables.class);
        registerEnumTabCompleter(MainHand.class);
        registerEnumTabCompleter(ItemFlag.class);
        registerEnumTabCompleter(InventoryType.class);
        registerEnumTabCompleter(MoonPhase.class);
        registerEnumTabCompleter(ItemRarity.class);
        registerEnumTabCompleter(EnchantmentRarity.class);
        registerEnumTabCompleter(GoalType.class);
        registerEnumTabCompleter(TargetBlockInfo.FluidMode.class);
        registerEnumTabCompleter(ClientOption.ChatVisibility.class);
        registerEnumTabCompleter(TextDecoration.class);
        registerEnumTabCompleter(BossBar.Color.class);
        registerEnumTabCompleter(BossBar.Flag.class);
        registerEnumTabCompleter(BossBar.Overlay.class);
        registerEnumTabCompleter(net.kyori.adventure.sound.Sound.Source.class);

        // TODO: Singleton completers

        registerTabCompleter(Sound.class, new SoundTabCompleter());
        registerTabCompleter(Material.class, new MaterialTabCompleter());
        registerTabCompleter(PotionEffectType.class, new PotionEffectTypeTabCompleter());
        registerTabCompleter(StructureType.class, new StructureTypeTabCompleter());

        final TabCompleter<Player> playerTabCompleter = (__) -> server.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toList());
        registerTabCompleter(Player.class, playerTabCompleter);
        registerTabCompleter(Audience.class, (string) -> {
            final List<String> audiences = playerTabCompleter.complete(string);
            audiences.add("*");
            audiences.add("console");
            return audiences;
        });

        registerTabCompleter(Plugin.class, (__) ->
                Arrays.stream(server.getPluginManager().getPlugins())
                        .map(Plugin::getName)
                        .collect(Collectors.toUnmodifiableList()));
        registerTabCompleter(Permission.class, (__) -> server.getPluginManager()
                .getPermissions()
                .stream()
                .map(Permission::getName)
                .collect(Collectors.toUnmodifiableList()));
        registerTabCompleter(Datapack.class, (__) -> server.getDatapackManager()
                .getPacks()
                .stream()
                .map(Datapack::getName)
                .collect(Collectors.toUnmodifiableList()));
        registerTabCompleter(World.class, (__) -> server.getWorlds()
                .stream()
                .map(World::getKey)
                .map(Key::asString)
                .collect(Collectors.toUnmodifiableList()));
    }

    private <T extends @NotNull Enum<@NotNull T>> void  registerEnumTabCompleter(
            final @NotNull Class<@NotNull T> enumType) {

        Objects.requireNonNull(enumType, "enumType cannot be null");

        registerTabCompleter(enumType, new EnumTabCompleter<>(enumType));
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
