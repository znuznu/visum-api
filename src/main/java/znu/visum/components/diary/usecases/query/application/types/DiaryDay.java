package znu.visum.components.diary.usecases.query.application.types;

public class DiaryDay {
  private Integer day;

  private DiaryMovieType movie;

  public DiaryDay() {}

  public DiaryDay(Integer day, DiaryMovieType movie) {
    this.day = day;
    this.movie = movie;
  }

  public Integer getDay() {
    return day;
  }

  public void setDay(Integer day) {
    this.day = day;
  }

  public DiaryMovieType getMovie() {
    return movie;
  }

  public void setMovie(DiaryMovieType movie) {
    this.movie = movie;
  }

  @Override
  public String toString() {
    return "DiaryDay{" + "day='" + day + "'," + "movie='" + movie + "'" + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiaryDay that = (DiaryDay) o;
    return java.util.Objects.equals(day, that.day) && java.util.Objects.equals(movie, that.movie);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(day, movie);
  }

  public static class Builder {
    private Integer day;

    private DiaryMovieType movie;

    public DiaryDay build() {
      DiaryDay result = new DiaryDay();
      result.day = this.day;
      result.movie = this.movie;
      return result;
    }

    public DiaryDay.Builder day(Integer day) {
      this.day = day;
      return this;
    }

    public DiaryDay.Builder movie(DiaryMovieType movie) {
      this.movie = movie;
      return this;
    }
  }
}
