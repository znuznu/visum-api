package znu.visum.core.pagination.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class VisumPage<T> {

  /** The current page number */
  private int current;

  /** The total number of desired elements */
  private int size;

  /** The total number of elements in all the pages */
  private long totalElements;

  /** The page content of type T */
  private List<T> content;

  /** True if the current page is the first one */
  private boolean isFirst;

  /** True if the current page is the last one */
  private boolean isLast;

  /** The total number of pages */
  private int totalPages;
}
