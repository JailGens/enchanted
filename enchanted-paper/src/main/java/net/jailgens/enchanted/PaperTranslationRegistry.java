package net.jailgens.enchanted;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * A registry of paper translations.
 *
 * @author Sparky983
 */
public interface PaperTranslationRegistry {

    @Contract(value = "_ -> new", pure = true)
    static PaperTranslationRegistry create(final @NotNull MiniMessage miniMessage) {

        return new PaperTranslationRegistryImpl(miniMessage);
    }

    @NotNull Component getTranslation(@NotNull String key,
                                      @NotNull Map<@NotNull String, @NotNull Object> placeholders);
}
