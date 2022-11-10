package net.jailgens.enchanted;

import net.jailgens.enchanted.annotations.Aliases;
import net.jailgens.enchanted.annotations.Command;
import net.jailgens.enchanted.annotations.Description;
import net.jailgens.enchanted.annotations.Usage;
import net.jailgens.mirror.AnnotationElement;
import net.jailgens.mirror.AnnotationValues;
import net.jailgens.mirror.Mirror;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link Mirror} based implementation of {@link CommandInfo}.
 *
 * @author Spark983
 */
final class MirrorCommandInfo implements CommandInfo {

    private static final @NotNull AnnotationElement NAME = AnnotationElement.value(Command.class);
    private static final @NotNull AnnotationElement ALIASES = AnnotationElement.value(Aliases.class);
    private static final @NotNull AnnotationElement USAGE = AnnotationElement.value(Usage.class);
    private static final @NotNull AnnotationElement DESCRIPTION = AnnotationElement.value(Description.class);

    private final AnnotationValues annotations;

    MirrorCommandInfo(final @NotNull AnnotationValues annotations) {

        Objects.requireNonNull(annotations, "annotations cannot be null");

        this.annotations = annotations;
    }

    @Override
    public @NotNull Optional<@NotNull String> getName() {

        return annotations.getString(NAME);
    }

    @Override
    public @Unmodifiable @NotNull List<@NotNull String> getAliases() {

        return annotations.getStrings(ALIASES);
    }

    @Override
    public @NotNull Optional<String> getUsage() {

        return annotations.getString(USAGE);
    }

    @Override
    public @NotNull Optional<@NotNull String> getDescription() {

        return annotations.getString(DESCRIPTION);
    }
}
