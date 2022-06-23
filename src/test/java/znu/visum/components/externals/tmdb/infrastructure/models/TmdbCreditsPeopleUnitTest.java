package znu.visum.components.externals.tmdb.infrastructure.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.components.externals.domain.ExternalDirector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("TmdbCreditsPeopleUnitTest")
class TmdbCreditsPeopleUnitTest {
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
    @DisplayName("When the director has a name made of two words, separated by a space")
    void itShouldReturnNameAndForenameWithASimpleSplitOnSpace() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName("Jacques Dupont");
      crewPeople.setJob("Director");
      crewPeople.setProfilePath("/poster1.jpg");

      assertThat(crewPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(
              new ExternalDirector(1, "Jacques", "Dupont", BASE_POSTER_URL + "/poster1.jpg"));
    }

    @Test
    @DisplayName("When the director has a name made of a single word")
    void itShouldReturnDirectorWithAnEmptyName() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName("Jacques");
      crewPeople.setJob("Director");
      crewPeople.setProfilePath("/poster1.jpg");

      assertThat(crewPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(new ExternalDirector(1, "Jacques", "", BASE_POSTER_URL + "/poster1.jpg"));
    }

    @Test
    @DisplayName("When the director has an empty name")
    void itShouldReturnDirectorWithAnEmptyNameAndForename() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName("");
      crewPeople.setJob("Director");
      crewPeople.setProfilePath("/poster1.jpg");

      assertThat(crewPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(new ExternalDirector(1, "", "", BASE_POSTER_URL + "/poster1.jpg"));
    }

    @Test
    @DisplayName("When the director has a name composed of more than 2 words separated by spaces")
    void itShouldReturnDirectorWithLeftPartAsForenameAndRightPartAsName() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName("Jacques Dupont Baguette Camembert");
      crewPeople.setJob("Director");
      crewPeople.setProfilePath("/poster1.jpg");

      assertThat(crewPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(
              new ExternalDirector(
                  1, "Jacques", "Dupont Baguette Camembert", BASE_POSTER_URL + "/poster1.jpg"));
    }

    @Test
    @DisplayName("When the director has a name composed one word but ending with a space")
    void itShouldReturnDirectorWithATrimForenameAndAnEmptyName() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName("Jacques ");
      crewPeople.setJob("Director");
      crewPeople.setProfilePath("/poster1.jpg");

      assertThat(crewPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(new ExternalDirector(1, "Jacques", "", BASE_POSTER_URL + "/poster1.jpg"));
    }

    @Test
    @DisplayName("When the director has a name starting with a space")
    void itShouldReturnDirectorWithATrimForename() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName(" Jacques");
      crewPeople.setJob("Director");
      crewPeople.setProfilePath("/poster1.jpg");

      assertThat(crewPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(new ExternalDirector(1, "Jacques", "", BASE_POSTER_URL + "/poster1.jpg"));
    }

    @Test
    @DisplayName("When the director no poster path")
    void itShouldReturnDirectorWithNullPosterUrl() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName(" Jacques");
      crewPeople.setJob("Director");
      crewPeople.setProfilePath(null);

      assertThat(crewPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(new ExternalDirector(1, "Jacques", "", null));
    }
  }
}
