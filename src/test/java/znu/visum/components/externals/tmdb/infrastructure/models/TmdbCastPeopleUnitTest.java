package znu.visum.components.externals.tmdb.infrastructure.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.components.externals.domain.ExternalActor;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TmdbCastPeopleUnitTest")
class TmdbCastPeopleUnitTest {

  private static final String BASE_POSTER_URL = "https://fake-url.io";

  @Nested
  class ToDomain {

    @Test
    @DisplayName("When the actor has a name made of two words, separated by a space")
    void itShouldReturnNameAndForenameWithASimpleSplitOnSpace() {
      TmdbCastPeople castPeople = new TmdbCastPeople();
      castPeople.setId(1);
      castPeople.setName("Jacques Dupont");
      castPeople.setProfilePath("/poster1.jpg");

      assertThat(castPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(new ExternalActor(1, "Jacques", "Dupont", BASE_POSTER_URL + "/poster1.jpg"));
    }

    @Test
    @DisplayName("When the actor has a name made of a single word")
    void itShouldReturnAnActorWithAnEmptyName() {
      TmdbCastPeople castPeople = new TmdbCastPeople();
      castPeople.setId(1);
      castPeople.setName("Jacques");
      castPeople.setProfilePath("/poster1.jpg");

      assertThat(castPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(new ExternalActor(1, "Jacques", "", BASE_POSTER_URL + "/poster1.jpg"));
    }

    @Test
    @DisplayName("When the actor has an empty name")
    void itShouldReturnAnActorWithAnEmptyNameAndForename() {
      TmdbCastPeople castPeople = new TmdbCastPeople();
      castPeople.setId(1);
      castPeople.setName("");
      castPeople.setProfilePath("/poster1.jpg");

      assertThat(castPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(new ExternalActor(1, "", "", BASE_POSTER_URL + "/poster1.jpg"));
    }

    @Test
    @DisplayName("When the actor has a name composed of more than 2 words separated by spaces")
    void itShouldReturnAnActorWithLeftPartAsForenameAndRightPartAsName() {
      TmdbCastPeople castPeople = new TmdbCastPeople();
      castPeople.setId(1);
      castPeople.setName("Jacques Dupont Baguette Camembert");
      castPeople.setProfilePath("/poster1.jpg");

      assertThat(castPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(
              new ExternalActor(
                  1, "Jacques", "Dupont Baguette Camembert", BASE_POSTER_URL + "/poster1.jpg"));
    }

    @Test
    @DisplayName("When the actor has a name composed one word but ending with a space")
    void itShouldReturnAnActorWithATrimForenameAndAnEmptyName() {
      TmdbCastPeople castPeople = new TmdbCastPeople();
      castPeople.setId(1);
      castPeople.setName("Jacques ");
      castPeople.setProfilePath("/poster1.jpg");

      assertThat(castPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(new ExternalActor(1, "Jacques", "", BASE_POSTER_URL + "/poster1.jpg"));
    }

    @Test
    @DisplayName("When the actor has a name starting with a space")
    void itShouldReturnAnActorWithATrimForename() {
      TmdbCastPeople castPeople = new TmdbCastPeople();
      castPeople.setId(1);
      castPeople.setName(" Jacques");
      castPeople.setProfilePath("/poster1.jpg");

      assertThat(castPeople.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(new ExternalActor(1, "Jacques", "", BASE_POSTER_URL + "/poster1.jpg"));
    }

    @Test
    @DisplayName("When the actor no poster path")
    void itShouldReturnActorWithNullPosterUrl() {
      TmdbCastPeople cast = new TmdbCastPeople();
      cast.setId(1);
      cast.setName(" Jacques");
      cast.setProfilePath(null);

      assertThat(cast.toDomainWithBasePosterUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(new ExternalActor(1, "Jacques", "", null));
    }
  }
}