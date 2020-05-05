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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** User: my5t3ry Date: 5/4/20 1:57 PM */
public class PrintService {

  public static void print(final SearchResult searchResult) {
    final int[] i = {0};

    String format = "%-3s %s";
    searchResult
        .getHits()
        .forEach(
            curHit -> {
              i[0]++;
              final List<String> descriptions = splitDescription(curHit);
              TerminalService.terminal
                  .writer()
                  .println(
                      String.format(format, i[0], extendWithColor(curHit.getTitel(), "yellow")));
              descriptions.forEach(
                  curDescription ->
                      TerminalService.terminal
                          .writer()
                          .println(
                              String.format(
                                  format, " ", extendWithColor(curDescription, "white"))));
              TerminalService.terminal
                  .writer()
                  .println(String.format(format, " ", extendWithColor(curHit.getUrl(), "green")));
              TerminalService.terminal.writer().println(" ");
            });
  }

  private static List<String> splitDescription(SearchHit curHit) {
    final List<String> result = new ArrayList<>();
    if (curHit.getDescription().length() + 3 >= TerminalService.terminal.getSize().getColumns()) {
      result.add(
          curHit
              .getDescription()
              .substring(0, TerminalService.terminal.getSize().getColumns() - 4));
      result.add(
          curHit
              .getDescription()
              .substring(
                  TerminalService.terminal.getSize().getColumns() - 4,
                  curHit.getDescription().length() - 1));
      return result;
    }

    return Arrays.asList(curHit.getDescription());
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
