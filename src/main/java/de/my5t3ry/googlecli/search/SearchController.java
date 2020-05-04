package de.my5t3ry.googlecli.search;

import de.my5t3ry.googlecli.config.PropertiesLoader;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** User: my5t3ry Date: 5/4/20 1:52 PM */
@NoArgsConstructor
public class SearchController {

  private final GoogleWebSearch googleWebSearch = new GoogleWebSearch();
  private final Printer printer = new Printer();
  private SearchQuery currentSearch;
  private SearchResult currentResult;
  private List<SearchHit> basket = new ArrayList<>();

  public void newSearch(String shPrompt) {
    currentSearch = new SearchQuery.Builder(shPrompt).build();
    search();
  }

  public void clearBasket() {
    basket = new ArrayList<>();
  }

  private void search() {
    printer.printLoadingInfo(currentSearch);
    currentResult = googleWebSearch.search(currentSearch);
    print();
  }

  private void print() {
    printer.clearScreen();
    printer.print(currentResult);
    printer.print(currentSearch, basket);
  }

  public void openLinks(boolean silent) {
    try {
      if (silent) {
        for (SearchHit curBasket : basket) {
          Runtime.getRuntime()
              .exec(
                  PropertiesLoader.properties.getProperty("open-url-command")
                      + " "
                      + curBasket.getUrl()
                      + " True");
        }
      } else {
        for (SearchHit curBasket : basket) {
          Runtime.getRuntime()
              .exec(
                  PropertiesLoader.properties.getProperty("open-url-command")
                      + " "
                      + curBasket.getUrl()
                      + " False");
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void nextPage() {
    if (Objects.isNull(currentSearch)) {
      printer.printWithColor("No search available", "red");
    } else {
      currentSearch.nextPage();
      search();
    }
  }

  public void lastPage() {
    if (Objects.isNull(currentSearch)) {
      printer.printWithColor("No search available", "red");
    } else {
      currentSearch.lastPage();
      search();
    }
  }

  public void addResultToBasket(int index) {
    basket.add(currentResult.getHits().get(index));
    print();
  }
}
