package znu.visum.business.accounts.persistence;

import znu.visum.business.accounts.models.Account;

public interface AccountDao {
    long count();

    void deleteById(long id);

    Account save(Account account);

    Account findByUsername(String username);
}
