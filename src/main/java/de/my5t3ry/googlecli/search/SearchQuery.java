package de.my5t3ry.googlecli.search;

import lombok.Getter;
import lombok.ToString;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/** Created on 15.07.2015 by afedulov */
@ToString
@Getter
public class SearchQuery {

  private final String query;
  private final String site;
  private final Integer numResults;
  private Integer start;

  public SearchQuery(Builder builder) {
    this.query = builder.query;
    this.site = builder.site;
    this.numResults = builder.numResults;
    start = 0;
  }

  public Integer currentPage() {
    if (start == 0) {
      return 1;
    }
    return (start / numResults) + 1;
  }

  public void nextPage() {
    this.start = this.start + numResults;
  }

  public void lastPage() {
    if (this.start - numResults >= 0) {
      this.start = this.start - numResults;
    }
  }

  public static class Builder {

    private String query;
    private String site;
    private Integer numResults = 10;

    public Builder(String query) {
      try {
        this.query = URLEncoder.encode(query, "UTF-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }

    public Builder site(String site) {
      this.site = site;
      return this;
    }

    public Builder numResults(Integer numResults) {
      this.numResults = numResults;
      return this;
    }

    public SearchQuery build() {
      return new SearchQuery(this);
    }
  }
}
