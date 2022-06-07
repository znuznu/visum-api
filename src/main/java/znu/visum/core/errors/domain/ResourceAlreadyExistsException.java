package znu.visum.core.errors.domain;

public abstract class ResourceAlreadyExistsException extends VisumException {

  protected ResourceAlreadyExistsException(DomainModel domainModel) {
    super(
        String.format("The given %s already exists.", domainModel),
        VisumExceptionStatus.BAD_REQUEST,
        "DATA_ALREADY_EXISTS");
  }
}
