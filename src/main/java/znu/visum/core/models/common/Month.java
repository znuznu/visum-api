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
    return switch (month) {
      case JANUARY -> Month.JANUARY;
      case FEBRUARY -> Month.FEBRUARY;
      case MARCH -> Month.MARCH;
      case APRIL -> Month.APRIL;
      case MAY -> Month.MAY;
      case JUNE -> Month.JUNE;
      case JULY -> Month.JULY;
      case AUGUST -> Month.AUGUST;
      case SEPTEMBER -> Month.SEPTEMBER;
      case OCTOBER -> Month.OCTOBER;
      case NOVEMBER -> Month.NOVEMBER;
      case DECEMBER -> Month.DECEMBER;
    };
  }

  public static java.time.Month toJava(Month month) {
    return switch (month) {
      case JANUARY -> java.time.Month.JANUARY;
      case FEBRUARY -> java.time.Month.FEBRUARY;
      case MARCH -> java.time.Month.MARCH;
      case APRIL -> java.time.Month.APRIL;
      case MAY -> java.time.Month.MAY;
      case JUNE -> java.time.Month.JUNE;
      case JULY -> java.time.Month.JULY;
      case AUGUST -> java.time.Month.AUGUST;
      case SEPTEMBER -> java.time.Month.SEPTEMBER;
      case OCTOBER -> java.time.Month.OCTOBER;
      case NOVEMBER -> java.time.Month.NOVEMBER;
      case DECEMBER -> java.time.Month.DECEMBER;
    };
  }

  public String getRepresentation() {
    return this.representation;
  }

  public int getNumericRepresentation() {
    return this.numericRepresentation;
  }
}
