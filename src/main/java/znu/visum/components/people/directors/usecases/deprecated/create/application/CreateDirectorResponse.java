package znu.visum.components.people.directors.usecases.deprecated.create.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.directors.domain.models.MovieFromDirector;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class CreateDirectorResponse {
  private final long id;

  private final String forename;

  private final String name;

  private final List<ResponseMovie> movies;

  public CreateDirectorResponse(long id, String forename, String name, List<ResponseMovie> movies) {
    this.id = id;
    this.forename = forename;
    this.name = name;
    this.movies = movies;
  }

  public static CreateDirectorResponse from(Director director) {
    return new CreateDirectorResponse(
        director.getId(),
        director.getForename(),
        director.getName(),
        director.getMovies().stream().map(ResponseMovie::from).collect(Collectors.toList()));
  }

  public long getId() {
    return id;
  }

  public String getForename() {
    return forename;
  }

  public String getName() {
    return name;
  }

  public List<ResponseMovie> getMovies() {
    return movies;
  }

  private static class ResponseMovie {
    private final Long id;

    private final String title;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private final LocalDate releaseDate;

    private final boolean isFavorite;

    private final boolean shouldWatch;

    public ResponseMovie(
        Long id, String title, LocalDate releaseDate, boolean isFavorite, boolean shouldWatch) {
      this.id = id;
      this.title = title;
      this.releaseDate = releaseDate;
      this.isFavorite = isFavorite;
      this.shouldWatch = shouldWatch;
    }

    static ResponseMovie from(MovieFromDirector movieFromDirector) {
      return new ResponseMovie(
          movieFromDirector.getId(),
          movieFromDirector.getTitle(),
          movieFromDirector.getReleaseDate(),
          movieFromDirector.isFavorite(),
          movieFromDirector.isShouldWatch());
    }

    public Long getId() {
      return id;
    }

    public String getTitle() {
      return title;
    }

    public LocalDate getReleaseDate() {
      return releaseDate;
    }

    public boolean isFavorite() {
      return isFavorite;
    }

    public boolean isShouldWatch() {
      return shouldWatch;
    }
  }
}
