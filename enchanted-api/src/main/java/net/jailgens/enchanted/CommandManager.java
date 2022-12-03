package net.jailgens.enchanted;

/**
 * The main manager for all commands.
 *
 * @author Sparky983
 * @since 0.0.0
 */
public interface CommandManager extends CommandFactory,
        CommandRegistry,
        ConverterRegistry,
        ArgumentParserRegistry {

}
