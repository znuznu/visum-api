package znu.visum.components.person.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Builder
@Getter
public class Identity {

  private final String forename;
  private final String name;

  public static Identity fromPlain(String plainName) {
    if (plainName == null) {
      throw new IllegalArgumentException("null plain name provided");
    }

    String plainTrimmed = plainName.trim();

    int firstDelimiterIndex = plainTrimmed.indexOf(" ");
    if (firstDelimiterIndex < 0) {
      return Identity.builder().name("").forename(plainTrimmed).build();
    } else {
      return Identity.builder()
          .name(plainTrimmed.substring(firstDelimiterIndex + 1))
          .forename(plainTrimmed.substring(0, firstDelimiterIndex))
          .build();
    }
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    Identity identity = (Identity) other;

    return forename.equals(identity.forename) && name.equals(identity.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(forename, name);
  }
}
