package znu.visum.business.tmdb.mappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MoviePreviewResults {
    @JsonProperty("page")
    private int page;

    private int totalPages;

    private int totalResults;

    @JsonProperty("results")
    private MoviePreview[] results;

    public MoviePreviewResults() {
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

    public MoviePreview[] getResults() {
        return results;
    }

    public void setResults(MoviePreview[] results) {
        this.results = results;
    }
}
