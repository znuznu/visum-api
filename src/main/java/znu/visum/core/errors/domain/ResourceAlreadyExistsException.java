package znu.visum.core.errors.domain;

public class ResourceAlreadyExistsException extends VisumException {
  public ResourceAlreadyExistsException(DomainModel domainModel) {
    super(String.format("The given %s already exists.", domainModel));
  }

  public static ResourceAlreadyExistsException with(DomainModel domainModel) {
    return new ResourceAlreadyExistsException(domainModel);
  }
}
