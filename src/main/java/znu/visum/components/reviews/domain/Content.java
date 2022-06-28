package znu.visum.components.reviews.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import znu.visum.core.assertions.VisumAssert;

@Getter
@EqualsAndHashCode
public class Content {

  private final String text;

  private Content(String text) {
    VisumAssert.notBlank("text", text);

    this.text = text;
  }

  public static Content of(String text) {
    return new Content(text);
  }
}
