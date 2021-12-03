package znu.visum.components.externals.tmdb.infrastructure.models;

import znu.visum.core.pagination.domain.VisumPage;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TmdbSearchResponseMapper {
  public static <T, V> VisumPage<V> toVisumPage(
      TmdbSearchResponse<T> searchResponse, Function<T, V> mapper) {
    return new VisumPage.Builder<V>()
        .current(searchResponse.getPage())
        .content(
            Arrays.stream(searchResponse.getResults()).map(mapper).collect(Collectors.toList()))
        .isFirst(searchResponse.getPage() == 1)
        .isLast(searchResponse.getPage() == searchResponse.getTotalPages())
        .size(searchResponse.getResults().length)
        .totalElements(searchResponse.getTotalResults())
        .totalPages(searchResponse.getTotalPages())
        .build();
  }
}
