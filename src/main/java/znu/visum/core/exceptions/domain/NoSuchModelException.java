package znu.visum.core.exceptions.domain;

public abstract class NoSuchModelException extends VisumException {

  protected NoSuchModelException(String id, Domain domain) {
    super(
        String.format("No %s with id %s found.", domain, id),
        VisumExceptionStatus.NOT_FOUND,
        "RESOURCE_NOT_FOUND");
  }
}
