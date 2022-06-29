package znu.visum.components.accounts.usecases.signup.application;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.accounts.usecases.signup.domain.Signup;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class SignupController {
  private final Signup signup;

  @Autowired
  public SignupController(Signup signup) {
    this.signup = signup;
  }

  @Operation(summary = "Create an account.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void signUp(@Valid @RequestBody final SignupRequest signupRequest) {
    this.signup.process(signupRequest.toDomain());
  }
}
