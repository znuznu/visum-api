package znu.visum.core.exceptions.domain;

public abstract class ResourceAlreadyExistsException extends VisumException {

  protected ResourceAlreadyExistsException(Domain domain) {
    super(
        String.format("The given %s already exists.", domain),
        VisumExceptionStatus.BAD_REQUEST,
        "DATA_ALREADY_EXISTS");
  }
}
