package znu.visum.components.accounts.usecases.signup.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import znu.visum.components.accounts.domain.AccountToRegister;

import javax.validation.constraints.NotBlank;

@Schema(description = "Represents an account to create.")
public class SignupRequest {
  @Schema(description = "The account's username.")
  @NotBlank
  private final String username;

  @Schema(description = "The account's password.")
  @NotBlank
  private final String password;

  @Schema(description = "The account's registrationKey.")
  @NotBlank
  private final String registrationKey;

  @JsonCreator
  public SignupRequest(
      @JsonProperty("username") String username,
      @JsonProperty("password") String password,
      @JsonProperty("registrationKey") String registrationKey) {
    this.username = username;
    this.password = password;
    this.registrationKey = registrationKey;
  }

  public AccountToRegister toDomain() {
    return new AccountToRegister(this.username, this.password, this.registrationKey);
  }
}
