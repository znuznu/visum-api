package znu.visum.components.diary.usecases.query.application.types;

public class DiaryFiltersInput {
  private int year;

  private Long genreId;

  private Integer grade;

  public DiaryFiltersInput() {}

  public DiaryFiltersInput(int year, Long genreId, Integer grade) {
    this.year = year;
    this.genreId = genreId;
    this.grade = grade;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public Long getGenreId() {
    return genreId;
  }

  public void setGenreId(Long genreId) {
    this.genreId = genreId;
  }

  public Integer getGrade() {
    return grade;
  }

  public void setGrade(Integer grade) {
    this.grade = grade;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DiaryFiltersInput that = (DiaryFiltersInput) o;
    return year == that.year
        && java.util.Objects.equals(genreId, that.genreId)
        && java.util.Objects.equals(grade, that.grade);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(year, genreId, grade);
  }

  public static class Builder {
    private int year;

    private Long genreId;

    private Integer grade;

    public DiaryFiltersInput build() {
      DiaryFiltersInput result = new DiaryFiltersInput();
      result.year = this.year;
      result.genreId = this.genreId;
      result.grade = this.grade;
      return result;
    }

    public DiaryFiltersInput.Builder year(int year) {
      this.year = year;
      return this;
    }

    public DiaryFiltersInput.Builder genreId(Long genreId) {
      this.genreId = genreId;
      return this;
    }

    public DiaryFiltersInput.Builder grade(Integer grade) {
      this.grade = grade;
      return this;
    }
  }
}
