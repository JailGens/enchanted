package net.jailgens.enchanted;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

/**
 * A paper {@link Inspectable}.
 *
 * @author Sparky983
 * @since 0.1.0
 */
public interface PaperInspectable extends Inspectable, PaperExecutable {

    @Override
    @NotNull @Unmodifiable List<
            ? extends @NotNull PaperCommandParameter<? extends @NotNull Object>> getParameters();
}
