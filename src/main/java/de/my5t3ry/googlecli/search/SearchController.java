package de.my5t3ry.googlecli.search;

import de.my5t3ry.googlecli.config.PropertiesService;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** User: my5t3ry Date: 5/4/20 1:52 PM */
@NoArgsConstructor
public class SearchController {

  private static final GoogleWebSearch googleWebSearch = new GoogleWebSearch();
  private static final Printer printer = new Printer();
  private static SearchQuery currentSearch;
  private static  SearchResult currentResult;
  private static  List<SearchHit> basket = new ArrayList<>();

  public static void newSearch(String shPrompt) {
    currentSearch = new SearchQuery.Builder(shPrompt).build();
    search();
  }

  public static void clearBasket() {
    basket = new ArrayList<>();
    print();
  }

  private static void search() {
    printer.printLoadingInfo(currentSearch);
    currentResult = googleWebSearch.search(currentSearch);
    print();
  }

  private static void print() {
    printer.clearScreen();
    printer.print(currentResult);
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

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void nextPage() {
    if (Objects.isNull(currentSearch)) {
      printer.printWithColor("No search available", "red");
    } else {
      currentSearch.nextPage();
      search();
    }
  }

  public static void lastPage() {
    if (Objects.isNull(currentSearch)) {
      printer.printWithColor("No search available", "red");
    } else {
      currentSearch.lastPage();
      search();
    }
  }

  public static void addResultToBasket(int index) {
    basket.add(currentResult.getHits().get(index));
    print();
  }
}
