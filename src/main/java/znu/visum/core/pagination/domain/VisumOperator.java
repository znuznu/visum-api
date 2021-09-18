package znu.visum.core.pagination.domain;

import java.util.regex.Pattern;

public enum VisumOperator {
  LTE("^(.*?)<=(.*?)$"),
  LT("^(.*?)<(.*?)$"),
  GTE("^(.*?)>=(.*?)$"),
  GT("^(.*?)>(.*?)$"),
  NULL("^(.*?)=null$"),
  IN("^(.*?)~(\\{.*?\\})$"),
  LIKE("^(.*?)=%(.*?)%$"),
  SW("^(.*?)=(.*?)%$"),
  EW("^(.*?)=%(.*?)$"),
  EQB("^(.*?)==(.*?)$"),
  EQ("^(.*?)=(.*?)$");

  private final Pattern pattern;

  VisumOperator(String regex) {
    this.pattern = Pattern.compile(regex);
  }

  public Pattern getPattern() {
    return this.pattern;
  }
}
