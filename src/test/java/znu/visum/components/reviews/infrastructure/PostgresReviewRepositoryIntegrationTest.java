package znu.visum.components.reviews.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import znu.visum.components.reviews.domain.ReviewRepository;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("PostgresReviewRepositoryIntegrationTest")
@ActiveProfiles("flyway")
class PostgresReviewRepositoryIntegrationTest {

  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private ReviewRepository reviewRepository;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @DisplayName("countAllByUpdateDateYear() - no reviews")
  @Test
  void itShouldReturnZero() {
    long count = this.reviewRepository.countAllByUpdateDateYear(Year.of(2021));

    assertThat(count).isZero();
  }

  @DisplayName("countAllByUpdateDateYear() - with reviews")
  @Test
  @Sql(
      scripts = {
        "/sql/truncate_all_tables.sql",
        "/sql/insert_multiple_movies_with_review_viewing_history_metadata.sql",
      })
  void itShouldReturnCountOfAllReviewsUpdatedIn2021() {
    long count = this.reviewRepository.countAllByUpdateDateYear(Year.of(2021));

    assertThat(count).isEqualTo(8);
  }
}
