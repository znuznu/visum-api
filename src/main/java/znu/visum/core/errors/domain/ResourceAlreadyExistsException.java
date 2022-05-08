package znu.visum.core.errors.domain;

public class ResourceAlreadyExistsException extends VisumException {

  public ResourceAlreadyExistsException(DomainModel domainModel) {
    super(
        String.format("The given %s already exists.", domainModel),
        VisumExceptionStatus.BAD_REQUEST,
        "DATA_ALREADY_EXISTS");
  }

  public static ResourceAlreadyExistsException with(DomainModel domainModel) {
    return new ResourceAlreadyExistsException(domainModel);
  }
}
