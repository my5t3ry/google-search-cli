package de.my5t3ry.googlecli.command;

import de.my5t3ry.googlecli.config.PropertiesService;
import de.my5t3ry.googlecli.search.SearchController;

/** User: my5t3ry Date: 5/4/20 10:00 PM */
public class NextCommand extends AbstractCommand {
  protected NextCommand() {
    super(PropertiesService.properties.getProperty("command.next"));
  }

  @Override
  public void execute(String command) {
    SearchController.nextPage();
  }
}
