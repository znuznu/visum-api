package znu.visum.core.models.common;

public enum Month {
  JANUARY("JAN", 1),
  FEBRUARY("FEB", 2),
  MARCH("MAR", 3),
  APRIL("APR", 4),
  MAY("MAY", 5),
  JUNE("JUN", 6),
  JULY("JUL", 7),
  AUGUST("AUG", 8),
  SEPTEMBER("SEP", 9),
  OCTOBER("OCT", 10),
  NOVEMBER("NOV", 11),
  DECEMBER("DEC", 12);

  private final String representation;
  private final int numericRepresentation;

  Month(String representation, int numericRepresentation) {
    this.representation = representation;
    this.numericRepresentation = numericRepresentation;
  }

  public static Month from(java.time.Month month) {
    switch (month) {
      case JANUARY:
        return Month.JANUARY;
      case FEBRUARY:
        return Month.FEBRUARY;
      case MARCH:
        return Month.MARCH;
      case APRIL:
        return Month.APRIL;
      case MAY:
        return Month.MAY;
      case JUNE:
        return Month.JUNE;
      case JULY:
        return Month.JULY;
      case AUGUST:
        return Month.AUGUST;
      case SEPTEMBER:
        return Month.SEPTEMBER;
      case OCTOBER:
        return Month.OCTOBER;
      case NOVEMBER:
        return Month.NOVEMBER;
      case DECEMBER:
        return Month.DECEMBER;
      default:
        throw new UnsupportedOperationException("A new month has been created by the Architect.");
    }
  }

  public String getRepresentation() {
    return this.representation;
  }

  public int getNumericRepresentation() {
    return this.numericRepresentation;
  }
}
