package de.my5t3ry.googlecli;

import de.my5t3ry.googlecli.command.AbstractCommand;
import de.my5t3ry.googlecli.command.CommandService;
import de.my5t3ry.googlecli.config.PropertiesService;
import de.my5t3ry.googlecli.history.GoogleSearchCliHistory;
import de.my5t3ry.googlecli.search.Printer;
import de.my5t3ry.googlecli.search.SearchController;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;

class GoogleSearchCliMain {
  private static final SearchController searchController = new SearchController();
  private static final Printer printer = new Printer();

  public static void main(String[] args) {
    PropertiesService.loadProperties();
    CommandService.initCommands();
    try {
      Terminal terminal = TerminalBuilder.builder().system(true).nativeSignals(true).build();
      LineReader lineReader =
          LineReaderBuilder.builder()
              .terminal(terminal)
              .history(new GoogleSearchCliHistory())
              .build();
      GoogleSearchCliMain.printer.clearScreen();
      while (true) {
        String line = null;
        boolean commandFound = false;
        try {
          line = lineReader.readLine("> ");
          if (line.equals(PropertiesService.properties.getProperty("command.exit"))) {
            return;
          }
          for (AbstractCommand curCommand : CommandService.getCommands()) {
            if (curCommand.executesCommand(line)) {
              curCommand.execute(line);
              commandFound = true;
            }
          }
          if (!commandFound) {
            GoogleSearchCliMain.searchController.newSearch(line);
          }
        } catch (UserInterruptException e) {
          // Ignore
        } catch (EndOfFileException e) {
          return;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
