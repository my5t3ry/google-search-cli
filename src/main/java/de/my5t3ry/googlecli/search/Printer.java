package de.my5t3ry.googlecli.search;

import lombok.NoArgsConstructor;
import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/** User: my5t3ry Date: 5/4/20 1:57 PM */
@NoArgsConstructor
public class Printer {

  private final int leftPadding = 20;

  public void print(final SearchResult searchResult) {
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

  private String extendWithColor(String value, String color) {
    return CommandLine.Help.Ansi.AUTO.string("@|" + color + " " + value + "|@");
  }

  public void printWithColor(String value, String color) {
    System.out.println(extendWithColor(value, color));
  }

  public void print(SearchQuery currentSearch, List<SearchHit> basket) {
    printWithColor(
        "page['" + currentSearch.currentPage() + "']" + ", basket['" + basket.size() + "']",
        "green");
  }

  public void printHelp() {
    clearScreen();
    try (BufferedReader br =
        new BufferedReader(
            new InputStreamReader(this.getClass().getResourceAsStream("/help.txt")))) {
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public void printLoadingInfo(SearchQuery currentSearch) {
    clearScreen();
    printWithColor(
        "searching for ['"
            + currentSearch.getQuery()
            + "'] "
            + " page['"
            + currentSearch.currentPage()
            + "']"
            + "...",
        "green");
  }
}
