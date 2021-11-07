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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("SignupRouteIntegrationTest")
@ActiveProfiles("flyway")
public class SignupRouteIntegrationTest {
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
  public void whenTheAccountHaveBeenSaved_itShouldReturnA201Response() throws Exception {
    mvc.perform(
            post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    "{\"username\": \"billy\",\"password\": \"pwd12345\",\"registrationKey\": \"test-registration-key\"}"))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  @Sql("/sql/truncate_account_table.sql")
  public void givenAWrongRegistrationKey_itShouldReturnA401Response() throws Exception {
    mvc.perform(
            post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    "{\"username\": \"billy\",\"password\": \"pwd12345\",\"registrationKey\": \"wrong\"}"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid registration key."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_REGISTRATION_KEY"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/accounts"));
  }

  @Test
  @Sql("/sql/insert_single_account.sql")
  public void whenTheMaximumAccountNumberHasBeenReached_itShouldReturnA403Response()
      throws Exception {
    mvc.perform(
            post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    "{\"username\": \"billy\",\"password\": \"pwd12345\",\"registrationKey\": \"test-registration-key\"}"))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.message").value("Number of maximum account reached."))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.code").value("MAXIMUM_NUMBER_OF_ACCOUNT_REACHED"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/accounts"));
  }

  @Nested
  class InvalidRequest {

    @Test
    public void givenABlankUsername_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post("/api/accounts")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(
                      "{\"username\": \"\",\"password\": \"something\",\"registrationKey\": \"test-registration-key\"}"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid body."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/accounts"));
    }

    @Test
    public void givenABlankPassword_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post("/api/accounts")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(
                      "{\"username\": \"billy\",\"password\": \"\",\"registrationKey\": \"test-registration-key\"}"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid body."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/accounts"));
    }

    @Test
    public void givenABlankRegistrationKey_itShouldReturnA400Response() throws Exception {
      mvc.perform(
              post("/api/accounts")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(
                      "{\"username\": \"billy\",\"password\": \"pwd1234\",\"registrationKey\": \"\"}"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid body."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_BODY"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/api/accounts"));
    }
  }
}
