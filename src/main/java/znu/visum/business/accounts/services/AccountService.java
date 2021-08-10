package znu.visum.business.accounts.services;

import znu.visum.business.accounts.models.Account;
import znu.visum.business.accounts.models.AccountRegistration;

public interface AccountService {
    long count();

    void deleteById(long id);

    Account create(Account account);

    void validatePasswords(AccountRegistration accountRegistration);

    void validateRegistrationKey(AccountRegistration accountRegistration);

    void validateMaximumAccounts(AccountRegistration accountRegistration);
}
