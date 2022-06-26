package znu.visum.components.reviews.domain;

import lombok.Getter;

@Getter
public class Grade {
  private static final int MIN_VALUE = 1;
  private static final int MAX_VALUE = 10;

  private final int value;

  public Grade(int value) {
    if (value < MIN_VALUE || value > MAX_VALUE) {
      throw new IllegalArgumentException("Grade" + value + " is out of range [1;10]");
    }
    this.value = value;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    Grade grade = (Grade) other;

    return value == grade.value;
  }

  @Override
  public int hashCode() {
    return value;
  }
}
