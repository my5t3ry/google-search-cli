package de.my5t3ry.googlecli.command;

import de.my5t3ry.googlecli.config.PropertiesService;
import de.my5t3ry.googlecli.search.SearchController;

/** User: my5t3ry Date: 5/4/20 10:00 PM */
public class NewSearchCommand extends AbstractCommand {
  protected NewSearchCommand() {
    super("any string", "starts new search");
  }

  @Override
  public boolean executesCommand(String command) {
    return true;
  }

  @Override
  public void execute(String command) {
    SearchController.newSearch(command);
  }
}
