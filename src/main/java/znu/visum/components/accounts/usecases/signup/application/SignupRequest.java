package znu.visum.components.accounts.usecases.signup.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import znu.visum.components.accounts.domain.models.AccountToRegister;

import javax.validation.constraints.NotBlank;

@ApiModel("Represents an account to create.")
public class SignupRequest {
  @ApiModelProperty("The account's username.")
  @NotBlank
  private final String username;

  @ApiModelProperty("The account's password.")
  @NotBlank
  private final String password;

  @ApiModelProperty("The account's registrationKey.")
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
