package de.my5t3ry.googlecli.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** User: my5t3ry Date: 5/4/20 9:53 PM */
public abstract class AbstractCommand {
  private final List<String> commands = new ArrayList<>();

  protected AbstractCommand(String commands) {
    this.commands.addAll(Arrays.asList(commands.split(",")));
  }

  public boolean executesCommand(final String command) {
    return commands.contains(command);
  }

  public abstract void execute(String command);
}
