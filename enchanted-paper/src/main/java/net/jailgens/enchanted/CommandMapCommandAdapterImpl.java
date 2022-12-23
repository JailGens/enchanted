package net.jailgens.enchanted;

import org.bukkit.command.Command;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

final class CommandMapCommandAdapterImpl implements CommandMapCommandAdapter {

    private final PaperTranslationRegistry paperTranslationRegistry;

    @Contract(pure = true)
    CommandMapCommandAdapterImpl(final @NotNull PaperTranslationRegistry paperTranslationRegistry) {

        Objects.requireNonNull(paperTranslationRegistry, "paperTranslationRegistry cannot be null");

        this.paperTranslationRegistry = paperTranslationRegistry;
    }

    @Override
    public @NotNull Command adapt(final @NotNull PaperCommand command) {

        Objects.requireNonNull(command, "command cannot be null");

        return new CommandMapCommand(command, paperTranslationRegistry);
    }
}
