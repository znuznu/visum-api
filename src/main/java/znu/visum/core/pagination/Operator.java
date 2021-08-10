package znu.visum.core.pagination;

import java.util.regex.Pattern;

public enum Operator {
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

    Operator(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return this.pattern;
    }
}
