package znu.visum.components.accounts.usecases.signup.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import znu.visum.components.accounts.domain.errors.InvalidRegistrationKeyException;
import znu.visum.components.accounts.domain.errors.MaximumAccountReachedException;
import znu.visum.components.accounts.domain.models.Account;
import znu.visum.components.accounts.domain.models.AccountToRegister;

public interface SignupService {
  Logger logger = LoggerFactory.getLogger(SignupService.class);

  long count();

  Account create(Account account);

  void register(AccountToRegister accountToRegister);

  default void validateRegistrationKey(
      AccountToRegister accountToRegister, String registrationKey) {
    if (!accountToRegister.getRegistrationKey().equals(registrationKey)) {
      throw new InvalidRegistrationKeyException();
    }
  }

  default void validateMaximumAccounts() {
    // TODO should probably be a config property
    if (this.count() != 0) {
      logger.error("Trying to add a new account.");
      throw new MaximumAccountReachedException();
    }
  }
}
