package znu.visum.components.history.domain;

import znu.visum.core.exceptions.domain.DomainModel;
import znu.visum.core.exceptions.domain.NoSuchModelException;

public class NoSuchViewingHistoryException extends NoSuchModelException {
  public NoSuchViewingHistoryException(String id) {
    super(id, DomainModel.VIEWING_HISTORY);
  }

  public static NoSuchViewingHistoryException with(String id) {
    return new NoSuchViewingHistoryException(id);
  }
}
