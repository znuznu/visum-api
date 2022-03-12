package znu.visum.core.models.common;

public enum Operator {
  EQUAL("="),
  IN("IN");

  private final String representation;

  Operator(String representation) {
    this.representation = representation;
  }

  public String getRepresentation() {
    return this.representation;
  }
}
