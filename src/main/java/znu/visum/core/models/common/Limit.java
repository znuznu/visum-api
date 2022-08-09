package znu.visum.core.models.common;


public record Limit(int value) {

    private static final int MIN_VALUE = 0;

    public Limit {
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException("Negative limit is not allowed.");
        }
    }
}
