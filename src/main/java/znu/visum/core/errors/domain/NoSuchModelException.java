package znu.visum.core.errors.domain;

public class NoSuchModelException extends VisumException {
  public NoSuchModelException(String id, DomainModel domainModel) {
    super(String.format("No %s with id %s found.", domainModel, id));
  }

  public static NoSuchModelException with(String id, DomainModel domainModel) {
    return new NoSuchModelException(id, domainModel);
  }
}
