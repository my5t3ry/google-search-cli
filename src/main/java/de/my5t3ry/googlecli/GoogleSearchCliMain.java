package de.my5t3ry.googlecli;

import de.my5t3ry.googlecli.history.GoogleSearchCliHistory;
import de.my5t3ry.googlecli.search.Printer;
import de.my5t3ry.googlecli.search.SearchController;
import org.apache.commons.lang3.StringUtils;
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
    try {
      Terminal terminal = TerminalBuilder.builder().system(true).nativeSignals(true).build();
      //      initTerminalReader(terminal);
      //      GoogleCliMain.readCharacter(terminal);
      LineReader lineReader =
          LineReaderBuilder.builder()
              .terminal(terminal)
              .history(new GoogleSearchCliHistory())
              .build();
      GoogleSearchCliMain.printer.clearScreen();
      while (true) {
        String line = null;
        try {
          line = lineReader.readLine("> ");
          if (line.startsWith("exit")) {
            return;
          } else if (line.equals("h") || line.equals("help")) {
            GoogleSearchCliMain.printer.printHelp();
          } else if (line.equals("n")) {
            GoogleSearchCliMain.searchController.nextPage();
          } else if (line.equals("p")) {
            GoogleSearchCliMain.searchController.lastPage();
          } else if (line.equals("o")) {
            GoogleSearchCliMain.searchController.openLinks(false);
          } else if (line.equals("os")) {
            GoogleSearchCliMain.searchController.openLinks(true);
          } else if (StringUtils.isNumeric(line)) {
            GoogleSearchCliMain.searchController.addResultToBasket(Integer.parseInt(line));
          } else {
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
