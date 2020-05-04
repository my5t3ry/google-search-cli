package de.my5t3ry.googlecli.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Created on 15.07.2015 by afedulov */
@Getter
@AllArgsConstructor
public class SearchHit {
  private final String titel;
  private final String url;
  private final String description;

}
