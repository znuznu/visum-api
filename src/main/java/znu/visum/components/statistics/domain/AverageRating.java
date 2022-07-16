package znu.visum.components.statistics.domain;

public record AverageRating(float rating) {

    public AverageRating {
        if (rating < 1.0f || rating > 10.0f) {
            throw new IllegalArgumentException("Average rating should be between [1.0,10.0]");
        }
    }
}
