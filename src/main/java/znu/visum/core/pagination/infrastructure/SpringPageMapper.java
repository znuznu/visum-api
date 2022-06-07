package znu.visum.core.pagination.infrastructure;

import org.springframework.data.domain.Page;
import znu.visum.core.pagination.domain.VisumPage;

import java.util.function.Function;
import java.util.stream.Collectors;

/** Converts Spring Page to our domain. */
public class SpringPageMapper {

  private SpringPageMapper() {}

  public static <S, T> VisumPage<T> toVisumPage(Page<S> page, Function<S, T> mapper) {
    return VisumPage.<T>builder()
        .current(page.getNumber())
        .totalPages(page.getTotalPages())
        .totalElements(page.getTotalElements())
        .content(page.getContent().stream().map(mapper).collect(Collectors.toList()))
        .isFirst(page.isFirst())
        .isLast(page.isLast())
        .size(page.getSize())
        .build();
  }
}
