package de.my5t3ry.googlecli.command;

import de.my5t3ry.googlecli.config.PropertiesService;
import de.my5t3ry.googlecli.search.SearchController;

/**
 * User: my5t3ry
 * Date: 5/4/20 10:03 PM
 */
public class ClearBasketCommand extends AbstractCommand {
    protected ClearBasketCommand() {
        super(PropertiesService.properties.getProperty("command.clear-basket"), "clear basket");
    }

    @Override
    public void execute(String command) {
        SearchController.clearBasket();
    }
}
