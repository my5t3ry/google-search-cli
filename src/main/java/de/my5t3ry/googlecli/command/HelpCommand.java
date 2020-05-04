package de.my5t3ry.googlecli.command;

import de.my5t3ry.googlecli.config.PropertiesService;
import de.my5t3ry.googlecli.search.Printer;

/**
 * User: my5t3ry
 * Date: 5/4/20 9:58 PM
 */
public class HelpCommand extends AbstractCommand {
    protected HelpCommand() {
        super(PropertiesService.properties.getProperty("command.help"), "show help");
    }

    @Override
    public void execute(String command) {
        Printer.printHelp();
    }
}
