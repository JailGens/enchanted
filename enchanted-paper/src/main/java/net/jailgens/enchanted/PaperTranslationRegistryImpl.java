package net.jailgens.enchanted;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public final class PaperTranslationRegistryImpl implements PaperTranslationRegistry {

    /**
     * A map of translation keys to their translations.
     */
    private static final @NotNull Map<@NotNull String, @NotNull String> translations = Map.of(
            TranslationKey.INVALID_ARGUMENT, "<red>Could not parse <argument>: <reason>.",
            TranslationKey.GENERIC_INVALID_ARGUMENT, "<red>Could not parse <argument>.",
            TranslationKey.INVALID_SENDER, "<red>You must be a <sender> to execute this command.",
            TranslationKey.INVALID_USAGE, "<red>Invalid usage, try: <usage>.",
            TranslationKey.INTERNAL_ERROR, "<red>An internal error occurred."
    );

    private final @NotNull MiniMessage miniMessage;

    @Contract(pure = true)
    PaperTranslationRegistryImpl(final @NotNull MiniMessage miniMessage) {

        Objects.requireNonNull(miniMessage, "miniMessage cannot be null");

        this.miniMessage = miniMessage;
    }

    @Override
    public @NotNull Component getTranslation(final @NotNull String key,
                                             final @NotNull Map<@NotNull String, @NotNull Object> placeholders) {

        final String format = translations.get(key);

        if (format == null) {
            return Component.text(key);
        }

        final TagResolver[] placeholderResolvers = placeholders
                .entrySet()
                .stream()
                .map((placeholder) -> Placeholder.unparsed(
                        placeholder.getKey(),
                        placeholder.getValue().toString()
                )).toArray(TagResolver[]::new);

        return miniMessage.deserialize(format, placeholderResolvers);
    }
}
