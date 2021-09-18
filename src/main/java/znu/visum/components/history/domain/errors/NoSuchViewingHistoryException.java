package znu.visum.components.history.domain.errors;

import znu.visum.core.errors.domain.DomainModel;
import znu.visum.core.errors.domain.NoSuchModelException;

public class NoSuchViewingHistoryException extends NoSuchModelException {
  public NoSuchViewingHistoryException(String id) {
    super(id, DomainModel.VIEWING_HISTORY);
  }

  public static NoSuchViewingHistoryException with(String id) {
    return new NoSuchViewingHistoryException(id);
  }
}
