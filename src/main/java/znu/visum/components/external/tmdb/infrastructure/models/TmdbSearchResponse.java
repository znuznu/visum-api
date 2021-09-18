package znu.visum.components.external.tmdb.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represent a response for each call on the <code>/3/search/T</code> endpoint
 *
 * @param <T> A TMDB resource (company, movie, collection...)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdbSearchResponse<T> {
  @JsonProperty("page")
  private int page;

  private int totalPages;

  private int totalResults;

  @JsonProperty("results")
  private T[] results;

  public TmdbSearchResponse() {}

  public TmdbSearchResponse(int page, int totalPages, int totalResults, T[] results) {
    this.page = page;
    this.totalPages = totalPages;
    this.totalResults = totalResults;
    this.results = results;
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  @JsonProperty("totalPages")
  public int getTotalPages() {
    return totalPages;
  }

  @JsonProperty("total_pages")
  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  @JsonProperty("totalResults")
  public int getTotalResults() {
    return totalResults;
  }

  @JsonProperty("total_results")
  public void setTotalResults(int totalResults) {
    this.totalResults = totalResults;
  }

  public T[] getResults() {
    return results;
  }

  public void setResults(T[] results) {
    this.results = results;
  }
}
