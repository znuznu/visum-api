package helpers.mappers;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMapperUnitTest {
  @Nested
  class AsJsonString {
    @Test
    public void givenAnObjectWithADate_shouldMapTheDatesAccordingly() {
      ObjectWithDate object =
          new ObjectWithDate(
              LocalDate.of(2000, 12, 13), LocalDateTime.of(2000, 12, 13, 23, 12, 54));

      assertThat(TestMapper.toJsonString(object))
          .isEqualTo("{\"localDate\":\"2000-12-13\",\"localDateTime\":\"2000-12-13T23:12:54\"}");
    }
  }
}
