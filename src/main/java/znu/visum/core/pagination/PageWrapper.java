package znu.visum.core.pagination;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Duo to an inexplicable mystery, it seems I cannot
 * use a classic @JsonComponent on a Page serializer because
 * of my 'default-view-inclusion' not being recognized.
 * Therefore I bypass this problematic behavior with this wrapper...
 */
public class PageWrapper<T> {
    @JsonView({
            PageWrapper.Views.Light.class
    })
    private final int number;

    @JsonView(PageWrapper.Views.Light.class)
    private final int size;

    @JsonView(PageWrapper.Views.Light.class)
    private final long totalElements;

    @JsonView(PageWrapper.Views.Light.class)
    private final List<T> content;

    @JsonView(PageWrapper.Views.Light.class)
    private final boolean isFirst;

    @JsonView(PageWrapper.Views.Light.class)
    private final boolean isLast;

    @JsonView(PageWrapper.Views.Light.class)
    private final int totalPages;

    public PageWrapper(Page<T> page) {
        this.number = page.getNumber();
        this.size = page.getSize();
        this.content = page.getContent();
        this.isFirst = page.isFirst();
        this.isLast = page.isLast();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
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

    public long getTotalElements() {
        return totalElements;
    }

    public static class Views {
        public interface Id {
        }

        public interface Light extends Id {
        }

        public interface Full extends Light {
        }

        public interface Reviews extends Light {
        }
    }
}
