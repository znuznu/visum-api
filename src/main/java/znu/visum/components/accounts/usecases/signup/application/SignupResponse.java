package znu.visum.components.accounts.usecases.signup.application;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import znu.visum.components.accounts.domain.Account;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
@Schema(description = "Represents an account freshly created.")
public class SignupResponse {

  @Schema(description = "The account's id.")
  @NotBlank
  private final long id;

  @Schema(description = "The account's username.")
  @NotBlank
  private final String username;

  public static SignupResponse of(Account account) {
    return new SignupResponse(account.id(), account.username());
  }
}
