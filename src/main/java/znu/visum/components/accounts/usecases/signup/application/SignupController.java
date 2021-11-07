package znu.visum.components.accounts.usecases.signup.application;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.components.accounts.usecases.signup.domain.SignupService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class SignupController {
  private final SignupService signupService;

  @Autowired
  public SignupController(SignupService signupService) {
    this.signupService = signupService;
  }

  @ApiOperation("Create an account.")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void signUp(@Valid @RequestBody final SignupRequest signupRequest) {
    this.signupService.register(signupRequest.toDomain());
  }
}
