package znu.visum.components.externals.tmdb.infrastructure.models;

import znu.visum.core.pagination.domain.VisumPage;

import java.util.Arrays;
import java.util.function.BiFunction;

public class TmdbPageResponseMapper {

  private TmdbPageResponseMapper() {}

  public static <T, V> VisumPage<V> toVisumPage(
      TmdbPageResponse<T> searchResponse, BiFunction<T, String, V> mapper, String rootUrl) {
    return VisumPage.<V>builder()
        .current(searchResponse.getPage())
        .content(
            Arrays.stream(searchResponse.getResults())
                .map(resource -> mapper.apply(resource, rootUrl))
                .toList())
        .isFirst(searchResponse.getPage() == 1)
        .isLast(searchResponse.getPage() == searchResponse.getTotalPages())
        .size(searchResponse.getResults().length)
        .totalElements(searchResponse.getTotalResults())
        .totalPages(searchResponse.getTotalPages())
        .build();
  }
}
