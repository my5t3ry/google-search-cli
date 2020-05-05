package de.my5t3ry.googlecli.search;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

/** Created on 15.07.2015 by afedulov */
public class GoogleWebSearch {

  private SearchConfig CONFIG = new SearchConfig();

  public GoogleWebSearch() {}

  public SearchResult search(SearchQuery query) {
    HttpEntity entity = getResponse(query).getEntity();
    List<SearchHit> hitsUrls = null;
    try {
      hitsUrls = parseResponse(EntityUtils.toString(entity));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return new SearchResult(query, hitsUrls);
  }

  public SearchResult search(String query, int numResults) {
    SearchQuery searchQuery = new SearchQuery.Builder(query).numResults(numResults).build();
    return search(searchQuery);
  }

  private HttpResponse getResponse(SearchQuery query) {
    LogFactory.getFactory()
        .setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    java.util.logging.Logger.getLogger("org.apache.http").setLevel(Level.OFF);
    String uri = getUri(query);
    //    log.debug("Complete URL: {}", uri);
    HttpClient client =
        HttpClientBuilder.create()
            .setDefaultRequestConfig(
                RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
            .build();
    HttpGet request = new HttpGet(uri);
    request.addHeader(
        "User-Agent",
        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) QtWebEngine/5.11.3 Chrome/65.0.3325.230 Safari/537.36");
    request.addHeader("dnt", "1");
    request.addHeader(
        "accept",
        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");

    HttpResponse result = null;
    try {
      result = client.execute(request);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  private String getUri(SearchQuery query) {
    String uri = CONFIG.getGoogleSearchUrl().replaceAll(CONFIG.PLHD_QUERY, query.getQuery());
    uri =
        uri.replaceAll(
            CONFIG.PLHD_RESULTS_NUM, ifPresent(CONFIG.PLHD_RESULTS_NUM, query.getNumResults()));
    uri =
        uri.replaceAll(
            CONFIG.PLHD_RESULTS_START, ifPresent(CONFIG.PLHD_RESULTS_START, query.getStart()));
    return uri;
  }

  private String ifPresent(String plhd, Object param) {
    if (param != null) {
      return plhd + param;
    } else {
      return "";
    }
  }

  protected List<SearchHit> parseResponse(String document) {
    Document searchDoc = Jsoup.parse(document);
    Elements contentDiv = searchDoc.select("div#search");
    List<SearchHit> hitsUrls = new ArrayList<SearchHit>();
    Elements articlesLinks = contentDiv.select("div.g"); // a with href
    Elements results = contentDiv.select("div[contains(@class, 'g')]"); // a with hr
    for (Element link : articlesLinks) {
      String linkHref = link.select("a[href]").attr("href");
      String name = link.select("h3").text();
      String desc = link.select("span.st").text();
      if (StringUtils.isNotBlank(linkHref)
          && StringUtils.isNotBlank(name)
          && StringUtils.isNotBlank(desc)) {
        hitsUrls.add(new SearchHit(name, linkHref, desc));
      }
    }
    return hitsUrls;
  }

  @Data
  public static class SearchConfig {
    /* Placeholders */
    private final String PLHD_QUERY = "__query__";
    private String PLHD_RESULTS_NUM = "&num=";
    private String PLHD_RESULTS_START = "&start=";
    private String PLHD_SITE = "&as_sitesearch=";
    private String PURE_URL_REGEX = "/url\\?q=(.*)&sa.*";
    private final Pattern PURE_URL_PATTERN = Pattern.compile(PURE_URL_REGEX);

    private String CACHE_URL = "http://webcache.googleusercontent.com";

    private String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com/search?";

    public String getGoogleSearchUrl() {
      return GOOGLE_SEARCH_URL_PREFIX + "q=" + PLHD_QUERY + PLHD_RESULTS_START + PLHD_RESULTS_NUM;
    }
  }
}
