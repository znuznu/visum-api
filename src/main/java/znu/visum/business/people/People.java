package znu.visum.business.people;

import com.fasterxml.jackson.annotation.JsonView;
import znu.visum.business.categories.movies.models.Movie;
import znu.visum.core.pagination.PageWrapper;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class People {
    @JsonView({
            Views.Light.class,
            Movie.Views.Light.class,
            PageWrapper.Views.Full.class
    })
    private String name;

    @JsonView({
            Views.Light.class,
            Movie.Views.Light.class,
            PageWrapper.Views.Full.class
    })
    private String forename;

    public People() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public static class Views {
        public interface Id {
        }

        public interface Light extends Id {
        }

        public interface Full extends Light {
        }
    }
}
