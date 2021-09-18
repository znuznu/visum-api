package znu.visum.core.pagination.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class PageSearch<T> implements Pageable {
  private long offset;
  private long limit;
  @JsonIgnore private Specification<T> search;
  private Sort sorting = Sort.unsorted();

  private PageSearch() {}

  protected PageSearch(PageSearch<T> pageSearch) {
    this.offset = pageSearch.offset;
    this.limit = pageSearch.limit;
    this.search = pageSearch.search;
    this.sorting = pageSearch.sorting;
  }

  private PageSearch(long limit, long offset, Specification<T> search, Sort sort) {
    this.limit = limit;
    this.offset = offset;
    this.search = search;
    this.sorting = sort;
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
    return new Builder<>().offset(offset + limit).build();
  }

  @Override
  public Pageable previousOrFirst() {
    return new Builder<>().offset(Math.max(offset - limit, 0)).build();
  }

  @Override
  public Pageable first() {
    return new Builder<>().offset(0).build();
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

  public static class Builder<T> {
    private final PageSearch<T> pageSearch;

    public Builder() {
      this.pageSearch = new PageSearch<>();
    }

    public Builder<T> offset(long offset) {
      this.pageSearch.offset = offset;
      return this;
    }

    public Builder<T> limit(long limit) {
      this.pageSearch.limit = limit;
      return this;
    }

    public Builder<T> search(Specification<T> search) {
      this.pageSearch.search = search;
      return this;
    }

    public Builder<T> sorting(Sort sorting) {
      this.pageSearch.sorting = sorting;
      return this;
    }

    public PageSearch<T> build() {
      return new PageSearch<>(pageSearch);
    }
  }
}
