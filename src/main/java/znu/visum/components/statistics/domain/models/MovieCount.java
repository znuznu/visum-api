package znu.visum.components.statistics.domain.models;

import znu.visum.core.models.common.Pair;

import java.util.List;

public class MovieCount {
  private List<Pair<Integer, Integer>> perYear;
  private List<Pair<String, Integer>> perGenre;
  private List<Pair<String, Integer>> perOriginalLanguage;

  public MovieCount(
      List<Pair<Integer, Integer>> perYear,
      List<Pair<String, Integer>> perGenre,
      List<Pair<String, Integer>> perOriginalLanguage) {
    this.perYear = perYear;
    this.perGenre = perGenre;
    this.perOriginalLanguage = perOriginalLanguage;
  }

  public List<Pair<Integer, Integer>> getPerYear() {
    return perYear;
  }

  public void setPerYear(List<Pair<Integer, Integer>> perYear) {
    this.perYear = perYear;
  }

  public List<Pair<String, Integer>> getPerGenre() {
    return perGenre;
  }

  public void setPerGenre(List<Pair<String, Integer>> perGenre) {
    this.perGenre = perGenre;
  }

  public List<Pair<String, Integer>> getPerOriginalLanguage() {
    return perOriginalLanguage;
  }

  public void setPerOriginalLanguage(List<Pair<String, Integer>> perOriginalLanguage) {
    this.perOriginalLanguage = perOriginalLanguage;
  }
}
