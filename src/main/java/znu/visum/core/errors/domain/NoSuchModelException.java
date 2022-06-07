package znu.visum.core.errors.domain;

public abstract class NoSuchModelException extends VisumException {

  protected NoSuchModelException(String id, DomainModel domainModel) {
    super(
        String.format("No %s with id %s found.", domainModel, id),
        VisumExceptionStatus.NOT_FOUND,
        "RESOURCE_NOT_FOUND");
  }
}
