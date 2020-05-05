package de.my5t3ry.googlecli.print;

import de.my5t3ry.googlecli.command.CommandService;
import de.my5t3ry.googlecli.config.PropertiesService;
import de.my5t3ry.googlecli.search.SearchController;
import de.my5t3ry.googlecli.search.SearchHit;
import de.my5t3ry.googlecli.search.SearchQuery;
import de.my5t3ry.googlecli.search.SearchResult;
import de.my5t3ry.googlecli.term.TerminalService;
import picocli.CommandLine;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** User: my5t3ry Date: 5/4/20 1:57 PM */
public class PrintService {

  private static PrintStream IOProvider= System.out;


  public static void print(final SearchResult searchResult) {
    final int[] i = {0};
    String format = "%-3s %s";
    searchResult
        .getHits()
        .forEach(
            curHit -> {
              i[0]++;
              final List<String> descriptions = splitDescription(curHit);
              IOProvider.println(
                  String.format(format, i[0], extendWithColor(curHit.getTitel(), "yellow")));
              descriptions.forEach(
                  curDescription ->
                      IOProvider.println(
                          String.format(format, " ", extendWithColor(curDescription, "white"))));
              IOProvider.println(
                  String.format(format, " ", extendWithColor(curHit.getUrl(), "green")));
              IOProvider.println(" ");
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
    return Collections.singletonList(curHit.getDescription());
  }

  private static String extendWithColor(String value, String color) {
    return CommandLine.Help.Ansi.AUTO.string("@|" + color + " " + value + "|@");
  }

  public static void printWithColor(String value, String color) {
    IOProvider.println(extendWithColor(value, color));
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
    IOProvider.println("commands");
    CommandService.getCommands()
        .forEach(
            curCommand ->
                IOProvider.println(
                    String.format(
                        format, curCommand.getCommandsAsString(), curCommand.getDescription())));
  }

  public static void clearScreen() {
    IOProvider.print("\033[H\033[2J");
    IOProvider.flush();
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
    IOProvider.println(
        "enter ['" + PropertiesService.properties.getProperty("command.help") + "'] for help");
  }
}
