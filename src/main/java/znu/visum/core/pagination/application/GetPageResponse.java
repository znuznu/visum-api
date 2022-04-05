package znu.visum.core.pagination.application;

import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.core.pagination.domain.VisumPage;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Schema(description = "Represents a Page.")
public class GetPageResponse<T> {
  @Schema(description = "The current page number")
  @Min(0)
  private int current;

  @Schema(description = "The number of elements in the current page")
  private int size;

  @Schema(description = "The number of elements in all the pages")
  @Min(0)
  private long totalElements;

  @Schema(description = "The actual content of type T in the page")
  private List<T> content;

  @Schema(description = "True if the current page is the first one")
  private boolean isFirst;

  @Schema(description = "True if the current page is the last one")
  private boolean isLast;

  @Schema(description = "The number of total pages")
  @Min(0)
  private int totalPages;

  public GetPageResponse() {}

  public static <S, T> GetPageResponse<T> from(VisumPage<S> page, Function<S, T> mapper) {
    return new GetPageResponse.Builder<T>()
        .current(page.getCurrent())
        .totalPages(page.getTotalPages())
        .totalElements(page.getTotalElements())
        .isFirst(page.isFirst())
        .isLast(page.isLast())
        .size(page.getSize())
        .content(page.getContent().stream().map(mapper).collect(Collectors.toList()))
        .build();
  }

  public int getCurrent() {
    return current;
  }

  public int getSize() {
    return size;
  }

  public long getTotalElements() {
    return totalElements;
  }

  public List<T> getContent() {
    return content;
  }

  public boolean isFirst() {
    return isFirst;
  }

  public boolean isLast() {
    return isLast;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public static final class Builder<T> {
    private final GetPageResponse<T> response;

    private Builder() {
      this.response = new GetPageResponse<>();
    }

    public Builder<T> current(int current) {
      response.current = current;
      return this;
    }

    public Builder<T> size(int size) {
      response.size = size;
      return this;
    }

    public Builder<T> totalElements(long totalElements) {
      response.totalElements = totalElements;
      return this;
    }

    public Builder<T> content(List<T> content) {
      response.content = content;
      return this;
    }

    public Builder<T> isFirst(boolean isFirst) {
      response.isFirst = isFirst;
      return this;
    }

    public Builder<T> isLast(boolean isLast) {
      response.isLast = isLast;
      return this;
    }

    public Builder<T> totalPages(int totalPages) {
      response.totalPages = totalPages;
      return this;
    }

    public GetPageResponse<T> build() {
      return response;
    }
  }
}
