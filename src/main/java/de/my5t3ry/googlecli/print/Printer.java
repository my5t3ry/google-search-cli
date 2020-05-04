package de.my5t3ry.googlecli.print;

import de.my5t3ry.googlecli.command.CommandService;
import de.my5t3ry.googlecli.search.SearchController;
import de.my5t3ry.googlecli.search.SearchHit;
import de.my5t3ry.googlecli.search.SearchQuery;
import de.my5t3ry.googlecli.search.SearchResult;
import lombok.NoArgsConstructor;
import picocli.CommandLine;

import java.util.List;

/** User: my5t3ry Date: 5/4/20 1:57 PM */
@NoArgsConstructor
public class Printer {

  public static void print(final SearchResult searchResult) {
    final int[] i = {0};
    String format = "%-3s %s\n";
    searchResult
        .getHits()
        .forEach(
            curHit -> {
              i[0]++;
              System.out.format(format, i[0], extendWithColor(curHit.getTitel(), "yellow"));
              System.out.format(format, " ", extendWithColor(curHit.getDescription(), "white"));
              System.out.format(format, " ", extendWithColor(curHit.getUrl(), "green"));
              System.out.println(" ");
            });
  }

  private static String extendWithColor(String value, String color) {
    return CommandLine.Help.Ansi.AUTO.string("@|" + color + " " + value + "|@");
  }

  public static void printWithColor(String value, String color) {
    System.out.println(extendWithColor(value, color));
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
    String format = "%-15s %s\n";
    System.out.println("commands");
    CommandService.getCommands()
        .forEach(
            curCommand ->
                System.out.format(format, curCommand.getCommandsAsString(), curCommand.getDescription()));
  }

  public static void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
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
}
