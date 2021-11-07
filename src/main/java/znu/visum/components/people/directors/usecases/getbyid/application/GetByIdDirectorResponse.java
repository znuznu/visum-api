package znu.visum.components.people.directors.usecases.getbyid.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import znu.visum.components.people.directors.domain.models.Director;
import znu.visum.components.people.directors.domain.models.MovieFromDirector;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApiModel("Represents a director.")
public class GetByIdDirectorResponse {
  @ApiModelProperty("The director identifier.")
  private final long id;

  @ApiModelProperty("The director forename.")
  private final String forename;

  @ApiModelProperty("The director name.")
  private final String name;

  @ApiModelProperty("The director movies.")
  private List<ResponseMovie> movies;

  public GetByIdDirectorResponse(
      long id, String name, String forename, List<ResponseMovie> movies) {
    this.id = id;
    this.forename = forename;
    this.name = name;
    this.movies = movies;
  }

  public static GetByIdDirectorResponse from(Director director) {
    return new GetByIdDirectorResponse(
        director.getId(),
        director.getName(),
        director.getForename(),
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

  public void setMovies(List<ResponseMovie> movies) {
    this.movies = movies;
  }

  public static class ResponseMovie {
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

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ResponseMovie that = (ResponseMovie) o;
      return isFavorite() == that.isFavorite()
          && isShouldWatch() == that.isShouldWatch()
          && Objects.equals(getId(), that.getId())
          && Objects.equals(getTitle(), that.getTitle())
          && Objects.equals(getReleaseDate(), that.getReleaseDate());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getId(), getTitle(), getReleaseDate(), isFavorite(), isShouldWatch());
    }
  }
}
