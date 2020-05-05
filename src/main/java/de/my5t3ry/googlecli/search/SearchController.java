package de.my5t3ry.googlecli.search;

import de.my5t3ry.googlecli.config.PropertiesService;
import de.my5t3ry.googlecli.print.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** User: my5t3ry Date: 5/4/20 1:52 PM */
public class SearchController {

  private static final GoogleWebSearch googleWebSearch = new GoogleWebSearch();
  private static final Printer printer = new Printer();
  private static SearchQuery currentSearch;
  private static int currentPage;
  private static List<SearchResult> currentResults;
  private static List<SearchHit> basket = new ArrayList<>();

  public static void newSearch(String shPrompt) {
    currentSearch = new SearchQuery.Builder(shPrompt).build();
    currentPage = 0;
    currentResults = new ArrayList<>();
    search();
  }

  public static void clearBasket() {
    basket = new ArrayList<>();
    print();
  }

  private static void search() {
    printer.printLoadingInfo(currentSearch);
    if (currentResults.isEmpty()) {
      currentResults.add(googleWebSearch.search(currentSearch));
    }
    if (currentResults.size() < currentPage + 2) {
      Thread newThread =
          new Thread(
              () -> {
                currentSearch.nextPage();
                currentResults.add(googleWebSearch.search(currentSearch));
              });
      newThread.start();
    }
    print();
  }

  private static void print() {
    while (currentResults.size() < currentPage + 1) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    printer.clearScreen();
    printer.print(currentResults.get(currentPage));
    printer.print(currentSearch, basket);
  }

  public static void openBasket(boolean silent) {
    try {
      if (silent) {
        for (SearchHit curBasket : basket) {
          Runtime.getRuntime()
              .exec(
                  PropertiesService.properties.getProperty("open-url-command.silent")
                      + " "
                      + curBasket.getUrl());
        }
      } else {
        for (SearchHit curBasket : basket) {
          Runtime.getRuntime()
              .exec(
                  PropertiesService.properties.getProperty("open-url-command")
                      + " "
                      + curBasket.getUrl());
        }
      }
      print();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void nextPage() {
    if (Objects.isNull(currentSearch)) {
      printer.printWithColor("No search available", "red");
    } else {
      currentPage++;
      search();
    }
  }

  public static void lastPage() {
    if (Objects.isNull(currentSearch)) {
      printer.printWithColor("No search available", "red");
    } else {
      if (currentPage > 0) {
        currentPage--;
        search();
      }
    }
  }

  public static int getCurrentPage() {
    return currentPage + 1;
  }

  public static void addResultToBasket(int index) {
    basket.add(currentResults.get(currentPage).getHits().get(index - 1));
    print();
  }
}
