package znu.visum.components.external.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.components.external.domain.models.ExternalDirector;
import znu.visum.components.external.tmdb.infrastructure.models.TmdbCrewPeople;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("TmdbCreditsPeopleUnitTest")
public class TmdbCreditsPeopleUnitTest {
  @Nested
  class ToDomainExternalDirector {

    @Test
    @DisplayName("When the people is not a director")
    public void itShouldThrow() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName("Jacques Dupont");
      crewPeople.setJob("Original Music Composer");

      assertThatThrownBy(crewPeople::toDomainExternalDirector)
          .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When the director has a name made of two words, separated by a space")
    public void itShouldReturnNameAndForenameWithASimpleSplitOnSpace() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName("Jacques Dupont");
      crewPeople.setJob("Director");

      assertThat(crewPeople.toDomainExternalDirector())
          .usingRecursiveComparison()
          .isEqualTo(new ExternalDirector(1, "Jacques", "Dupont"));
    }

    @Test
    @DisplayName("When the director has a name made of a single word")
    public void itShouldReturnAnDirectorWithAnEmptyName() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName("Jacques");
      crewPeople.setJob("Director");

      assertThat(crewPeople.toDomainExternalDirector())
          .usingRecursiveComparison()
          .isEqualTo(new ExternalDirector(1, "Jacques", ""));
    }

    @Test
    @DisplayName("When the director has an empty name")
    public void itShouldReturnAnDirectorWithAnEmptyNameAndForename() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName("");
      crewPeople.setJob("Director");

      assertThat(crewPeople.toDomainExternalDirector())
          .usingRecursiveComparison()
          .isEqualTo(new ExternalDirector(1, "", ""));
    }

    @Test
    @DisplayName("When the director has a name composed of more than 2 words separated by spaces")
    public void itShouldReturnAnDirectorWithLeftPartAsForenameAndRightPartAsName() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName("Jacques Dupont Baguette Camembert");
      crewPeople.setJob("Director");

      assertThat(crewPeople.toDomainExternalDirector())
          .usingRecursiveComparison()
          .isEqualTo(new ExternalDirector(1, "Jacques", "Dupont Baguette Camembert"));
    }

    @Test
    @DisplayName("When the director has a name composed one word but ending with a space")
    public void itShouldReturnAnDirectorWithATrimForenameAndAnEmptyName() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName("Jacques ");
      crewPeople.setJob("Director");

      assertThat(crewPeople.toDomainExternalDirector())
          .usingRecursiveComparison()
          .isEqualTo(new ExternalDirector(1, "Jacques", ""));
    }

    @Test
    @DisplayName("When the director has a name starting with a space")
    public void itShouldReturnAnDirectorWithATrimForename() {
      TmdbCrewPeople crewPeople = new TmdbCrewPeople();
      crewPeople.setId(1);
      crewPeople.setName(" Jacques");
      crewPeople.setJob("Director");

      assertThat(crewPeople.toDomainExternalDirector())
          .usingRecursiveComparison()
          .isEqualTo(new ExternalDirector(1, "Jacques", ""));
    }
  }
}
