package znu.visum.core.exceptions.domain;

public enum Domain {
  ACTOR("actor"),
  DIRECTOR("director"),
  GENRE("genre"),
  REVIEW("review"),
  MOVIE("movie"),
  VIEWING_HISTORY("viewing history"),
  EXTERNAL_MOVIE("external movie");

  private final String representation;

  Domain(String representation) {
    this.representation = representation;
  }

  @Override
  public String toString() {
    return this.representation;
  }
}
