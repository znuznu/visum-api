package znu.visum.components.person.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IdentityUnitTest {

  @ParameterizedTest
  @CsvSource(
      value = {
        "Jacques Dupont/Jacques:Dupont",
        "Jacques/Jacques:",
        "Jacques Dupont Baguette Camembert/Jacques:Dupont Baguette Camembert",
        "Jacques /Jacques:",
        " Jacques/Jacques:"
      },
      delimiter = '/')
  void shouldExtractNameAndForename(
      String name, @ConvertWith(IdentityConverter.class) Identity expected) {
    var identity = Identity.fromPlain(name);
    assertThat(identity).isEqualTo(expected);
  }

  @Test
  void shouldThrowWhenNullPlain() {
    assertThatThrownBy(() -> Identity.fromPlain(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("null plain name provided");
  }

  static class IdentityConverter implements ArgumentConverter {

    @Override
    public Object convert(Object source, ParameterContext context)
        throws ArgumentConversionException {
      if (!(source instanceof String)) {
        throw new IllegalArgumentException("Source should be a String");
      }

      String[] parts = ((String) source).split(":");
      if (parts.length > 2) {
        throw new IllegalArgumentException("More than 2 maximum arguments found");
      }

      if (parts.length == 0) {
        return Identity.builder().forename("").name("").build();
      }

      if (parts.length == 1) {
        return Identity.builder().forename(parts[0]).name("").build();
      }

      return Identity.builder().forename(parts[0]).name(parts[1]).build();
    }
  }
}
