package znu.visum.components.externals.tmdb.infrastructure.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.components.externals.domain.ExternalDirector;
import znu.visum.components.person.domain.Identity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TmdbCreditsPersonUnitTest {
  @Nested
  class ToDomainExternalDirector {

    private static final String BASE_POSTER_URL = "https://fake-url.io";

    @Test
    @DisplayName("When the people is not a director")
    void itShouldThrow() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName("Jacques Dupont");
      crewPeople.setJob("Original Music Composer");
      crewPeople.setProfilePath("/poster1.jpg");

      assertThatThrownBy(() -> crewPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When the director has no poster path")
    void itShouldReturnDirectorWithNullPosterUrl() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName(" Jacques");
      crewPeople.setJob("Director");
      crewPeople.setProfilePath(null);

      assertThat(crewPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(
              new ExternalDirector(
                  1, Identity.builder().forename("Jacques").name("").build(), null));
    }
  }
}
