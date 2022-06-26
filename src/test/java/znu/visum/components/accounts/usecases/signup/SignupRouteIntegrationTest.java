package znu.visum.components.accounts.usecases.signup;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("SignupRouteIntegrationTest")
@ActiveProfiles("flyway")
class SignupRouteIntegrationTest {
  @Container
  private static final PostgreSQLContainer container = new PostgreSQLContainer("postgres:12.4");

  @Autowired private MockMvc mvc;

  @DynamicPropertySource
  static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.username", container::getUsername);
  }

  @Test
  void whenTheAccountHaveBeenSaved_itShouldReturnA201Response() throws Exception {
    mvc.perform(
            post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    "{\"username\": \"billy\",\"password\": \"pwd12345\",\"registrationKey\": \"test-registration-key\"}"))
        .andExpect(status().isCreated());
  }

  @Test
  @Sql("/sql/truncate_account_table.sql")
  void givenAWrongRegistrationKey_itShouldReturnA401Response() throws Exception {
    mvc.perform(
            post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    "{\"username\": \"billy\",\"password\": \"pwd12345\",\"registrationKey\": \"wrong\"}"))
        .andExpect(jsonPath("$.message").value("Invalid registration key."))
        .andExpect(jsonPath("$.code").value("INVALID_REGISTRATION_KEY"))
        .andExpect(jsonPath("$.path").value("/api/accounts"));
  }

  @Test
  @Sql("/sql/insert_single_account.sql")
  void whenTheMaximumAccountNumberHasBeenReached_itShouldReturnA403Response() throws Exception {
    mvc.perform(
            post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    "{\"username\": \"billy\",\"password\": \"pwd12345\",\"registrationKey\": \"test-registration-key\"}"))
        .andExpect(jsonPath("$.message").value("Number of maximum account reached."))
        .andExpect(jsonPath("$.code").value("MAXIMUM_NUMBER_OF_ACCOUNT_REACHED"))
        .andExpect(jsonPath("$.path").value("/api/accounts"));
  }

  @Nested
  class InvalidRequest {

    @Test
    void givenABlankUsername_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post("/api/accounts")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(
                      "{\"username\": \"\",\"password\": \"something\",\"registrationKey\": \"test-registration-key\"}"))
          .andExpect(jsonPath("$.message").value("username: must not be blank"))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value("/api/accounts"));
    }

    @Test
    void givenABlankPassword_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post("/api/accounts")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(
                      "{\"username\": \"billy\",\"password\": \"\",\"registrationKey\": \"test-registration-key\"}"))
          .andExpect(jsonPath("$.message").value("password: must not be blank"))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value("/api/accounts"));
    }

    @Test
    void givenABlankRegistrationKey_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post("/api/accounts")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(
                      "{\"username\": \"billy\",\"password\": \"pwd1234\",\"registrationKey\": \"\"}"))
          .andExpect(jsonPath("$.message").value("registrationKey: must not be blank"))
          .andExpect(jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(jsonPath("$.path").value("/api/accounts"));
    }
  }
}
