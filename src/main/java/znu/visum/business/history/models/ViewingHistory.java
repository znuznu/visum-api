package znu.visum.business.history.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import znu.visum.business.categories.movies.models.Movie;

import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
public abstract class ViewingHistory {
    @JsonView({
            Views.Light.class,
            Movie.Views.Light.class,
    })
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate viewingDate;

    public ViewingHistory() {
    }

    public LocalDate getViewingDate() {
        return viewingDate;
    }

    public void setViewingDate(LocalDate viewingDate) {
        this.viewingDate = viewingDate;
    }

    public static class Views {
        public interface Id {
        }

        public interface Light extends Views.Id {
        }

        public interface Full extends Views.Light {
        }
    }
}
