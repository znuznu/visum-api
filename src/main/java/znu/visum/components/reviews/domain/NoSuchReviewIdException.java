package znu.visum.components.reviews.domain;

import znu.visum.core.exceptions.domain.DomainModel;
import znu.visum.core.exceptions.domain.NoSuchModelException;

public class NoSuchReviewIdException extends NoSuchModelException {
  private NoSuchReviewIdException(String id) {
    super(id, DomainModel.REVIEW);
  }

  public static NoSuchReviewIdException with(String id) {
    return new NoSuchReviewIdException(id);
  }
}
