package znu.visum.business.accounts.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import znu.visum.business.accounts.models.Account;
import znu.visum.business.accounts.models.AccountRegistration;
import znu.visum.business.accounts.services.AccountService;


@RestController
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@ExposesResourceFor(Account.class)
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody AccountRegistration accountRegistration) {
        this.accountService.validatePasswords(accountRegistration);
        this.accountService.validateRegistrationKey(accountRegistration);
        this.accountService.validateMaximumAccounts(accountRegistration);
        this.accountService.create(accountRegistration.toAccount());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        accountService.deleteById(id);
    }
}
