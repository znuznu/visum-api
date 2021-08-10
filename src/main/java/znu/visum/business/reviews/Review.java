package znu.visum.business.reviews;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import znu.visum.business.categories.movies.models.Movie;
import znu.visum.core.pagination.PageWrapper;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Review {
    @JsonView({
            Views.Light.class,
            Movie.Views.Light.class,
            PageWrapper.Views.Light.class
    })
    private int grade;

    @JsonView({
            Views.Light.class,
            Movie.Views.Full.class,
            PageWrapper.Views.Light.class
    })
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @JsonView({
            Views.Light.class,
            Movie.Views.Full.class,
            PageWrapper.Views.Light.class
    })
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @JsonView({
            Views.Light.class,
            Movie.Views.Full.class,
            PageWrapper.Views.Light.class
    })
    private String content;

    public Review() {
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public static class Views {
        public interface Id {
        }

        public interface Light extends Id {
        }

        public interface Full extends Light {
        }

        public interface WithTitleAndIdOfContent {
        }
    }
}
