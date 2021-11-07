package znu.visum.components.reviews.domain.errors;

import znu.visum.core.errors.domain.DomainModel;
import znu.visum.core.errors.domain.NoSuchModelException;

public class NoSuchReviewIdException extends NoSuchModelException {
  public NoSuchReviewIdException(String id) {
    super(id, DomainModel.REVIEW);
  }

  public static NoSuchReviewIdException with(String id) {
    return new NoSuchReviewIdException(id);
  }
}
