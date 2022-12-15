package net.jailgens.enchanted.tabcompleters;

import net.jailgens.enchanted.TabCompleter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A generic {@link Enum} {@link TabCompleter}.
 *
 * @author Sparky983
 * @param <T> the type of the enum.
 */
public final class EnumTabCompleter<@NotNull T extends @NotNull Enum<@NotNull T>>
        implements TabCompleter<@NotNull T> {

    private final @NotNull List<@NotNull String> enumConstantNames;

    @Contract(pure = true)
    public EnumTabCompleter(final @NotNull Class<@NotNull T> enumType) {

        this(enumType, Enum::name);
    }

    public EnumTabCompleter(final @NotNull Class<@NotNull T> enumType,
                            final @NotNull Function<@NotNull T, @NotNull String> nameGenerator) {

        Objects.requireNonNull(enumType, "enumType cannot be null");

        this.enumConstantNames = Arrays.stream(enumType.getEnumConstants())
                .map(nameGenerator)
                .map((string) -> string.toLowerCase(Locale.ROOT))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public @NotNull List<@NotNull String> complete(final @NotNull String string) {

        return enumConstantNames;
    }
}
