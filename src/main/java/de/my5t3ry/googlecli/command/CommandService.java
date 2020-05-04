package de.my5t3ry.googlecli.command;

import java.util.ArrayList;
import java.util.List;

/** User: my5t3ry Date: 5/4/20 9:52 PM */
public class CommandService {
  private static List<AbstractCommand> commands = new ArrayList<>();

  public static void initCommands() {
    commands.add(new HelpCommand());
    commands.add(new NextCommand());
    commands.add(new PreviousCommand());
    commands.add(new ClearBasketCommand());
    commands.add(new OpenBasketCommand());
    commands.add(new OpenBasketSilentCommand());
    commands.add(new AddToBasketCommand());
  }

  public static List<AbstractCommand> getCommands() {
    return commands;
  }
}
