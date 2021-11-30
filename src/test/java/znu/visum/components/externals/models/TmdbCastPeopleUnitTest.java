package znu.visum.components.externals.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.components.externals.domain.models.ExternalActor;
import znu.visum.components.externals.tmdb.infrastructure.models.TmdbCastPeople;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TmdbCastPeopleUnitTest")
public class TmdbCastPeopleUnitTest {
  @Nested
  class ToDomain {

    @Test
    @DisplayName("When the actor has a name made of two words, separated by a space")
    public void itShouldReturnNameAndForenameWithASimpleSplitOnSpace() {
      TmdbCastPeople castPeople = new TmdbCastPeople();
      castPeople.setId(1);
      castPeople.setName("Jacques Dupont");

      assertThat(castPeople.toDomain())
          .usingRecursiveComparison()
          .isEqualTo(new ExternalActor(1, "Jacques", "Dupont"));
    }

    @Test
    @DisplayName("When the actor has a name made of a single word")
    public void itShouldReturnAnActorWithAnEmptyName() {
      TmdbCastPeople castPeople = new TmdbCastPeople();
      castPeople.setId(1);
      castPeople.setName("Jacques");

      assertThat(castPeople.toDomain())
          .usingRecursiveComparison()
          .isEqualTo(new ExternalActor(1, "Jacques", ""));
    }

    @Test
    @DisplayName("When the actor has an empty name")
    public void itShouldReturnAnActorWithAnEmptyNameAndForename() {
      TmdbCastPeople castPeople = new TmdbCastPeople();
      castPeople.setId(1);
      castPeople.setName("");

      assertThat(castPeople.toDomain())
          .usingRecursiveComparison()
          .isEqualTo(new ExternalActor(1, "", ""));
    }

    @Test
    @DisplayName("When the actor has a name composed of more than 2 words separated by spaces")
    public void itShouldReturnAnActorWithLeftPartAsForenameAndRightPartAsName() {
      TmdbCastPeople castPeople = new TmdbCastPeople();
      castPeople.setId(1);
      castPeople.setName("Jacques Dupont Baguette Camembert");

      assertThat(castPeople.toDomain())
          .usingRecursiveComparison()
          .isEqualTo(new ExternalActor(1, "Jacques", "Dupont Baguette Camembert"));
    }

    @Test
    @DisplayName("When the actor has a name composed one word but ending with a space")
    public void itShouldReturnAnActorWithATrimForenameAndAnEmptyName() {
      TmdbCastPeople castPeople = new TmdbCastPeople();
      castPeople.setId(1);
      castPeople.setName("Jacques ");

      assertThat(castPeople.toDomain())
          .usingRecursiveComparison()
          .isEqualTo(new ExternalActor(1, "Jacques", ""));
    }

    @Test
    @DisplayName("When the actor has a name starting with a space")
    public void itShouldReturnAnActorWithATrimForename() {
      TmdbCastPeople castPeople = new TmdbCastPeople();
      castPeople.setId(1);
      castPeople.setName(" Jacques");

      assertThat(castPeople.toDomain())
          .usingRecursiveComparison()
          .isEqualTo(new ExternalActor(1, "Jacques", ""));
    }
  }
}
