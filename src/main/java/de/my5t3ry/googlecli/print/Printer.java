package de.my5t3ry.googlecli.print;

import de.my5t3ry.googlecli.command.CommandService;
import de.my5t3ry.googlecli.config.PropertiesService;
import de.my5t3ry.googlecli.search.SearchController;
import de.my5t3ry.googlecli.search.SearchHit;
import de.my5t3ry.googlecli.search.SearchQuery;
import de.my5t3ry.googlecli.search.SearchResult;
import de.my5t3ry.googlecli.term.TerminalService;
import org.jline.utils.InfoCmp;
import picocli.CommandLine;

import java.util.List;

/** User: my5t3ry Date: 5/4/20 1:57 PM */
public class Printer {

  public static void print(final SearchResult searchResult) {
    final int[] i = {0};
    String format = "%-3s %s";
    searchResult
        .getHits()
        .forEach(
            curHit -> {
              i[0]++;
              TerminalService.terminal
                  .writer()
                  .println(
                      String.format(format, i[0], extendWithColor(curHit.getTitel(), "yellow")));
              TerminalService.terminal
                  .writer()
                  .println(
                      String.format(
                          format, " ", extendWithColor(curHit.getDescription(), "white")));
              TerminalService.terminal
                  .writer()
                  .println(String.format(format, " ", extendWithColor(curHit.getUrl(), "green")));
              TerminalService.terminal.writer().println(" ");
            });
  }

  private static String extendWithColor(String value, String color) {
    return CommandLine.Help.Ansi.AUTO.string("@|" + color + " " + value + "|@");
  }

  public static void printWithColor(String value, String color) {
    TerminalService.terminal.writer().println(extendWithColor(value, color));
  }

  public static void print(SearchQuery currentSearch, List<SearchHit> basket) {
    printWithColor(
        "page['"
            + SearchController.getCurrentPage()
            + "']"
            + " basket['"
            + basket.size()
            + "'] "
            + "query['"
            + currentSearch.getQuery().replaceAll("\\+", " ")
            + "'] ",
        "green");
  }

  public static void printHelp() {
    clearScreen();
    String format = "%-15s %s";
    TerminalService.terminal.writer().println("commands");
    CommandService.getCommands()
        .forEach(
            curCommand ->
                TerminalService.terminal
                    .writer()
                    .println(
                        String.format(
                            format,
                            curCommand.getCommandsAsString(),
                            curCommand.getDescription())));
  }

  public static void clearScreen() {
    TerminalService.terminal.puts(InfoCmp.Capability.clear_screen);
  }

  public static void printLoadingInfo(SearchQuery currentSearch) {
    clearScreen();
    printWithColor(
        "searching for ['"
            + currentSearch.getQuery().replaceAll("\\+", " ")
            + "'] "
            + " page['"
            + SearchController.getCurrentPage()
            + "']"
            + "...",
        "green");
  }

  public static void printStartMessage() {
    clearScreen();
    TerminalService.terminal
        .writer()
        .println(
            "enter ['" + PropertiesService.properties.getProperty("command.help") + "'] for help");
  }
}
