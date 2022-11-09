package znu.visum.components.externals.tmdb.infrastructure.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import znu.visum.components.externals.domain.models.ExternalCastMember;
import znu.visum.components.movies.domain.Role;
import znu.visum.components.person.domain.Identity;

import static org.assertj.core.api.Assertions.assertThat;

class TmdbCastPersonUnitTest {

  private static final String BASE_POSTER_URL = "https://fake-url.io";

  @Nested
  class ToDomainWithRootUrl {

    @Test
    @DisplayName("When the actor no poster path")
    void itShouldReturnActorWithNullPosterUrl() {
      TmdbCastPeople cast = new TmdbCastPeople();
      cast.setId(1);
      cast.setName("Jacques Dupont");
      cast.setProfilePath(null);
      cast.setCharacter("Bobby Billy");
      cast.setOrder(2);

      assertThat(cast.toDomainWithRootUrl(BASE_POSTER_URL))
          .usingRecursiveComparison()
          .isEqualTo(
              new ExternalCastMember(
                  1,
                  Identity.builder().forename("Jacques").name("Dupont").build(),
                  new Role("Bobby Billy", 2),
                  null));
    }
  }
}
