package net.jailgens.enchanted;

import com.destroystokyo.paper.ClientOption;
import com.destroystokyo.paper.block.TargetBlockInfo.FluidMode;
import com.destroystokyo.paper.entity.ai.GoalType;
import io.papermc.paper.datapack.Datapack;
import io.papermc.paper.enchantments.EnchantmentRarity;
import io.papermc.paper.inventory.ItemRarity;
import io.papermc.paper.world.MoonPhase;
import net.jailgens.enchanted.converter.SharedConverterRegistry;
import net.jailgens.enchanted.converters.DifficultyConverter;
import net.jailgens.enchanted.converters.EnumConverter;
import net.jailgens.enchanted.converters.GameModeConverter;
import net.jailgens.enchanted.converters.SearchConverter;
import net.jailgens.enchanted.converters.SoundConverter;
import net.jailgens.enchanted.converters.WorldConverter;
import net.jailgens.enchanted.parser.SharedArgumentParserRegistry;
import net.jailgens.enchanted.resolver.SharedArgumentResolverRegistry;
import net.jailgens.mirror.Mirror;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Art;
import org.bukkit.ChatColor;
import org.bukkit.CoalType;
import org.bukkit.CropState;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.FireworkEffect;
import org.bukkit.Fluid;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Note;
import org.bukkit.Particle;
import org.bukkit.PortalType;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.Statistic;
import org.bukkit.StructureType;
import org.bukkit.TreeSpecies;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
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
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.awt.*;
import java.lang.annotation.Annotation;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * A {@link CommandManager} decorator for the paper platform.
 *
 * @author Sparky983
 */
final class PaperCommandManagerImpl implements PaperCommandManager {

    private final @NotNull PaperCommandFactory commandFactory;
    private final @NotNull PaperCommandRegistry commandRegistry;
    private final @NotNull ConverterRegistry converterRegistry;
    private final @NotNull ArgumentParserRegistry argumentParserRegistry;
    private final @NotNull ArgumentResolverRegistry argumentResolverRegistry;
    private final @NotNull TabCompleterRegistry tabCompleterRegistry;

    PaperCommandManagerImpl(final @NotNull Plugin plugin) {

        Objects.requireNonNull(plugin, "plugin cannot be null");

        final Mirror mirror = Mirror.builder().build();
        final PaperAdapter adapter = new PaperAdapterImpl(this);
        final Server server = plugin.getServer();

        this.commandFactory = new PaperCommandFactoryImpl(
                new SharedCommandFactory(mirror, this),
                adapter
        );

        this.commandRegistry = new PaperCommandRegistryImpl(
                new PaperCommandMap(
                        CommandMap.create(),
                        new CommandMapCommandAdapterImpl(),
                        plugin.getName().toLowerCase(),
                        server.getCommandMap()
                ),
                adapter
        );

        this.converterRegistry = new SharedConverterRegistry();

        this.argumentParserRegistry = new SharedArgumentParserRegistry();
        this.argumentResolverRegistry = new SharedArgumentResolverRegistry();
        this.tabCompleterRegistry = new TabCompleterRegistryImpl(server);
    }

    /**
     * Helper method that registers all converters.
     *
     * @param server the server.
     */
    private void registerConverters(final @NotNull Server server) {

        assert server != null;

        registerSearchEnumConverter(Art.class);
        registerSearchEnumConverter("Chat color", ChatColor.class);
        registerSearchEnumConverter("Coal type", CoalType.class);
        registerSearchEnumConverter("Crop state", CropState.class);
        registerConverter(Difficulty.class,
                new SearchConverter<>("Difficulty", new DifficultyConverter()));
        registerSearchEnumConverter(Effect.class);
        registerSearchEnumConverter("Entity effect", EntityEffect.class);
        registerSearchEnumConverter(Fluid.class);
        registerSearchEnumConverter("Firework effect type", FireworkEffect.Type.class);
        // registerSearchEnumConverter("Game event", org.bukkit.GameEvent.class); // TODO: 1.17
        registerSearchEnumConverter(Instrument.class);
        registerSearchEnumConverter("Note tone", Note.Tone.class);
        registerSearchEnumConverter(Particle.class);
        registerSearchEnumConverter("Portal type", PortalType.class);
        registerSearchEnumConverter("Sound category", SoundCategory.class);
        registerSearchEnumConverter(Statistic.class);
        registerSearchEnumConverter("Statistic type", Statistic.Type.class);
        registerSearchEnumConverter("Tree species", TreeSpecies.class);
        registerSearchEnumConverter("Weather type", WeatherType.class);
        registerSearchEnumConverter("World environment", Environment.class);
        registerSearchEnumConverter("World type", WorldType.class);
        registerSearchEnumConverter("Potion type", PotionType.class);
        registerSearchEnumConverter("Permission default", PermissionDefault.class);
        registerSearchEnumConverter("Loot table", LootTables.class);
        registerSearchEnumConverter("Hand", MainHand.class);
        registerSearchEnumConverter("Item flag", ItemFlag.class);
        registerSearchEnumConverter("Inventory type", InventoryType.class);
        registerSearchEnumConverter("Moon phase", MoonPhase.class);
        registerSearchEnumConverter("Item rarity", ItemRarity.class);
        registerSearchEnumConverter("Enchantment rarity", EnchantmentRarity.class);
        registerSearchEnumConverter("Goal type", GoalType.class);
        registerSearchEnumConverter("Fluid mode", FluidMode.class);
        registerSearchEnumConverter("Chat visibility", ClientOption.ChatVisibility.class);
        registerSearchEnumConverter("Text decoration", TextDecoration.class);
        registerSearchEnumConverter("Bossbar color", BossBar.Color.class);
        registerSearchEnumConverter("Bossbar flag", BossBar.Flag.class);
        registerSearchEnumConverter("Bossbar overlay", BossBar.Overlay.class);
        registerSearchEnumConverter("Sound source", net.kyori.adventure.sound.Sound.Source.class);

        registerSearchEnumConverter("Time unit", TimeUnit.class);
        registerSearchEnumConverter(Month.class);
        registerSearchEnumConverter("Day", DayOfWeek.class);

        final Converter<NamespacedKey> keyConverter = (string) -> {
            final NamespacedKey key = NamespacedKey.fromString(string);
            if (key == null) {
                throw new ArgumentParseException("\"" + string + "\" is not a valid namespaced key");
            }
            return Optional.of(key);
        };

        registerConverter(NamespacedKey.class, keyConverter);
        registerConverter(Key.class, keyConverter);
        registerConverter(Component.class, (string) ->
                Optional.of(LegacyComponentSerializer.legacyAmpersand().deserialize(string)));
        registerConverter(TextColor.class, (string) -> {
            final TextColor color = TextColor.fromHexString(string);
            if (color == null) {
                throw new ArgumentParseException("Color \"" + string + "\" is invalid");
            }
            return Optional.of(color);
        });
        registerConverter(UUID.class, (string) -> {
            try {
                return Optional.of(UUID.fromString(string));
            } catch (final IllegalArgumentException e) {
                throw new ArgumentParseException("UUID \"" + string + "\" is invalid", e);
            }
        });
        registerConverter(Pattern.class, (string) -> {
            try {
                return Optional.of(Pattern.compile(string));
            } catch (final PatternSyntaxException e) {
                throw new ArgumentParseException("Pattern \"" + string + "\" is invalid", e);
            }
        });
        registerConverter(Color.class, (string) -> {
            try {
                return Optional.of(Color.decode(string));
            } catch (final NumberFormatException e) {
                throw new ArgumentParseException("Color \"" + string + "\" is invalid", e);
            }
        });

        registerConverter(Sound.class, new SearchConverter<>("Sound", new SoundConverter(keyConverter)));
        registerConverter(Material.class, new SearchConverter<>("Material",
                (string) -> Optional.ofNullable(Material.matchMaterial(string))));
        registerConverter(PotionEffectType.class, new SearchConverter<>("Potion effect type",
                (string) -> Optional.ofNullable(PotionEffectType.getByName(string))));
        registerConverter(StructureType.class, new SearchConverter<>("Structure type",
                (string) -> Optional.ofNullable(StructureType.getStructureTypes().get(string))));
        registerConverter(GameMode.class,
                new SearchConverter<>("Game mode", new GameModeConverter()));

        final Converter<Player> playerConverter = new SearchConverter<>("Player",
                (string) -> Optional.ofNullable(server.getPlayerExact(string)));
        final Converter<CommandSender> senderConverter = (string) -> {
            if (string.equalsIgnoreCase("console")) {
                return Optional.of(server.getConsoleSender());
            }
            return playerConverter.convert(string);
        };
        registerConverter(Player.class, playerConverter);
        registerConverter(CommandSender.class, senderConverter);
        registerConverter(Audience.class, (string) -> {
            if (string.equals("*")) {
                return Optional.of(server);
            }
            return senderConverter.convert(string);
        });
        registerConverter(Plugin.class, new SearchConverter<>("Plugin",
                (string) -> Optional.ofNullable(server.getPluginManager().getPlugin(string))));
        registerConverter(Permission.class, new SearchConverter<>("Permission",
                (string) -> Optional.ofNullable(server.getPluginManager().getPermission(string))));
        registerConverter(Datapack.class, new SearchConverter<>("Datapack",
                (string) -> server.getDatapackManager()
                        .getPacks()
                        .stream()
                        .filter(pack -> pack.getName().equals(string))
                        .findAny()));
        registerConverter(World.class, new SearchConverter<>("World", new WorldConverter(server)));
    }

    private <T extends @NotNull Enum<@NotNull T>> void registerSearchEnumConverter(
            final @NotNull Class<@NotNull T> enumType) {

        registerSearchEnumConverter(enumType.getSimpleName(), enumType);
    }

    @Contract(mutates = "this")
    private <T extends @NotNull Enum<@NotNull T>> void registerSearchEnumConverter(
            final @NotNull String name,
            final @NotNull Class<@NotNull T> enumType) {

        assert name != null;
        assert enumType != null;

        registerConverter(enumType, new SearchConverter<>(name, new EnumConverter<>(enumType)));
    }

    @Override
    public @NotNull PaperCommandGroup createCommand(final @NotNull Object command) {

        Objects.requireNonNull(command, "command cannot be null");

        return commandFactory.createCommand(command);
    }

    @Override
    public @NotNull PaperCommandGroup registerCommand(final @NotNull Object command) {

        Objects.requireNonNull(command, "command cannot be null");

        final PaperCommandGroup paperCommandGroup = createCommand(command);
        registerCommand(paperCommandGroup);
        return paperCommandGroup;
    }

    @Override
    public @NotNull PaperCommand registerCommand(final @NotNull PaperCommand command) {

        Objects.requireNonNull(command, "command cannot be null");

        return commandRegistry.registerCommand(command);
    }

    @Override
    public @NotNull PaperCommand registerCommand(@NotNull final Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        return commandRegistry.registerCommand(command);
    }

    @Override
    public void unregisterCommand(final @NotNull Command command) {

        Objects.requireNonNull(command, "command cannot be null");

        commandRegistry.unregisterCommand(command);
    }

    @Override
    public @NotNull Optional<? extends @NotNull Command> getCommand(
            @Subst("command-name") final @NotNull String label) {

        Objects.requireNonNull(label, "label cannot be null");

        return commandRegistry.getCommand(label);
    }

    @Override
    public @NotNull @Unmodifiable Collection<? extends @NotNull Command> getRegisteredCommands() {

        return commandRegistry.getRegisteredCommands();
    }

    @Override
    public <T extends @NotNull Object> void registerConverter(
            final @NotNull Class<@NotNull T> type,
            final @NotNull Converter<? extends @NotNull T> converter) {

        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(converter, "converter cannot be null");

        converterRegistry.registerConverter(type, converter);
    }

    @Override
    public <T extends @NotNull Object> Optional<? extends @NotNull Converter<? extends @NotNull T>>
    getConverter(final @NotNull Class<@NotNull T> type) {

        Objects.requireNonNull(type, "type cannot be null");

        return converterRegistry.getConverter(type);
    }

    @Override
    public boolean hasConverter(final @NotNull Class<? extends @NotNull Object> type) {

        Objects.requireNonNull(type, "type cannot be null");

        return converterRegistry.hasConverter(type);
    }

    @Override
    public <T extends @NotNull Annotation> void registerArgumentParser(
            final @NotNull Class<@NotNull T> annotationType,
            final @NotNull ArgumentParser<@NotNull T> argumentParser) {

        Objects.requireNonNull(annotationType, "annotationType cannot be null");
        Objects.requireNonNull(argumentParser, "argumentParser cannot be null");

        argumentParserRegistry.registerArgumentParser(annotationType, argumentParser);
    }

    @Override
    public <T extends @NotNull Annotation> @NotNull Optional<
            ? extends @NotNull ArgumentParser<@NotNull T>>
    getArgumentParser(final @NotNull Class<@NotNull T> annotationType) {

        Objects.requireNonNull(annotationType, "annotationType cannot be null");

        return argumentParserRegistry.getArgumentParser(annotationType);
    }

    @Override
    public <T extends @NotNull Annotation> void registerArgumentResolver(
            final @NotNull Class<@NotNull T> annotationType,
            final @NotNull ArgumentResolver<T> resolver) {

        Objects.requireNonNull(annotationType, "annotationType cannot be null");
        Objects.requireNonNull(resolver, "resolver cannot be null");

        argumentResolverRegistry.registerArgumentResolver(annotationType, resolver);
    }

    @Override
    public @NotNull <T extends @NotNull Annotation>
    Optional<? extends @NotNull ArgumentResolver<@NotNull T>>
    getArgumentResolver(final @NotNull Class<@NotNull T> annotationType) {

        return argumentResolverRegistry.getArgumentResolver(annotationType);
    }

    @Override
    public <T extends @NotNull Object> void registerTabCompleter(
            final @NotNull Class<@NotNull T> type,
            final @NotNull TabCompleter<@NotNull T> completer) {

        Objects.requireNonNull(type, "type cannot be null");
        Objects.requireNonNull(completer, "completer cannot be null");

        tabCompleterRegistry.registerTabCompleter(type, completer);
    }

    @Override
    public @NotNull <T extends @NotNull Object> TabCompleter<@NotNull T>
    getTabCompleter(final @NotNull Class<T> type) {

        Objects.requireNonNull(type, "type cannot be null");

        return tabCompleterRegistry.getTabCompleter(type);
    }
}
