package znu.visum.core.assertions;

import znu.visum.core.exceptions.domain.InvalidStringException;
import znu.visum.core.exceptions.domain.MissingMandatoryFieldException;

public final class VisumAssert {

  private VisumAssert() {}

  public static void notNull(String field, Object o) {
    if (o == null) {
      throw MissingMandatoryFieldException.forNullValue(field);
    }
  }

  public static void notBlank(String field, String text) {
    VisumAssert.notNull(field, text);

    if (text.isBlank()) {
      throw InvalidStringException.forBlankValue(field);
    }
  }
}
