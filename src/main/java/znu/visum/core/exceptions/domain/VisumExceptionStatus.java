package znu.visum.core.exceptions.domain;

public enum VisumExceptionStatus {
  BAD_REQUEST("Bad Request"),
  UNAUTHORIZED("Unauthorized"),
  FORBIDDEN("Forbidden"),
  NOT_FOUND("Not Found"),
  INTERNAL_SERVER_ERROR("Internal Server Error");

  private final String representation;

  VisumExceptionStatus(String representation) {
    this.representation = representation;
  }

  public String getRepresentation() {
    return this.representation;
  }
}
