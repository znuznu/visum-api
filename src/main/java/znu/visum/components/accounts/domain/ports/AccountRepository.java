package znu.visum.components.accounts.domain.ports;

import znu.visum.components.accounts.domain.models.Account;

import java.util.Optional;

public interface AccountRepository {
  long count();

  Account save(Account account);

  Optional<Account> findByUsername(String username);
}
