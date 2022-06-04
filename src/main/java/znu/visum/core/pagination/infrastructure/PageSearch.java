package znu.visum.core.pagination.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
@Builder
public class PageSearch<T> implements Pageable {
  private long offset;
  private long limit;
  @JsonIgnore private Specification<T> search;
  private Sort sorting = Sort.unsorted();

  protected PageSearch(PageSearch<T> pageSearch) {
    this.offset = pageSearch.offset;
    this.limit = pageSearch.limit;
    this.search = pageSearch.search;
    this.sorting = pageSearch.sorting;
  }

  public PageSearch(Pageable pageable) {
    this(pageable.getPageSize(), pageable.getOffset());
  }

  private PageSearch(long limit, long offset) {
    this.limit = limit;
    this.offset = offset;
  }

  @Override
  public int getPageNumber() {
    return (int) (offset / (double) limit);
  }

  @Override
  public int getPageSize() {
    return (int) limit;
  }

  @Override
  public long getOffset() {
    return offset;
  }

  @Override
  public Sort getSort() {
    return sorting;
  }

  @Override
  public Pageable next() {
    return builder().offset(offset + limit).build();
  }

  @Override
  public Pageable previousOrFirst() {
    return builder().offset(Math.max(offset - limit, 0)).build();
  }

  @Override
  public Pageable first() {
    return builder().offset(0).build();
  }

  @Override
  public Pageable withPage(int pageNumber) {
    // TODO
    return builder().build();
  }

  @Override
  public boolean hasPrevious() {
    return offset < limit;
  }

  public long getLimit() {
    return limit;
  }

  public Specification<T> getSearch() {
    return search;
  }
}
