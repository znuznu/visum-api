package znu.visum.core.pagination.domain;

import java.util.List;

public class VisumPage<T> {
  private int current;

  private int size;

  private long totalElements;

  private List<T> content;

  private boolean isFirst;

  private boolean isLast;

  private int totalPages;

  private VisumPage() {}

  /**
   * A page used in the pagination context.
   *
   * @param current The current page number
   * @param size The total number of desired elements
   * @param totalElements The total number of elements in all the pages
   * @param content - The actual content of type T in the page
   * @param isFirst True if the current page is the first one
   * @param isLast True if the current page is the last one
   * @param totalPages The total number of pages
   */
  public VisumPage(
      int current,
      int size,
      long totalElements,
      List<T> content,
      boolean isFirst,
      boolean isLast,
      int totalPages) {
    this.current = current;
    this.size = size;
    this.totalElements = totalElements;
    this.content = content;
    this.isFirst = isFirst;
    this.isLast = isLast;
    this.totalPages = totalPages;
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
    private final VisumPage<T> visumPage;

    public Builder() {
      this.visumPage = new VisumPage<T>();
    }

    public Builder<T> current(int current) {
      visumPage.current = current;
      return this;
    }

    public Builder<T> size(int size) {
      visumPage.size = size;
      return this;
    }

    public Builder<T> totalElements(long totalElements) {
      visumPage.totalElements = totalElements;
      return this;
    }

    public Builder<T> content(List<T> content) {
      visumPage.content = content;
      return this;
    }

    public Builder<T> isFirst(boolean isFirst) {
      visumPage.isFirst = isFirst;
      return this;
    }

    public Builder<T> isLast(boolean isLast) {
      visumPage.isLast = isLast;
      return this;
    }

    public Builder<T> totalPages(int totalPages) {
      visumPage.totalPages = totalPages;
      return this;
    }

    public VisumPage<T> build() {
      return this.visumPage;
    }
  }
}
