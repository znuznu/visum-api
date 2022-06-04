package znu.visum.core.pagination.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import znu.visum.core.pagination.domain.VisumPage;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
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

  public static <S, T> GetPageResponse<T> from(VisumPage<S> page, Function<S, T> mapper) {
    return GetPageResponse.<T>builder()
        .current(page.getCurrent())
        .totalPages(page.getTotalPages())
        .totalElements(page.getTotalElements())
        .isFirst(page.isFirst())
        .isLast(page.isLast())
        .size(page.getSize())
        .content(page.getContent().stream().map(mapper).collect(Collectors.toList()))
        .build();
  }
}
