package de.my5t3ry.googlecli.command;

import de.my5t3ry.googlecli.search.SearchController;
import org.apache.commons.lang3.StringUtils;

/**
 * User: my5t3ry
 * Date: 5/4/20 10:03 PM
 */
public class AddToBasketCommand extends AbstractCommand {
    protected AddToBasketCommand() {
        super("any integer", "add search hit to basket");
    }

    @Override
    public boolean executesCommand(String command) {
        return StringUtils.isNumeric(command);
    }

    @Override
    public void execute(String command) {
        SearchController.addResultToBasket(Integer.parseInt(command));
    }
}
