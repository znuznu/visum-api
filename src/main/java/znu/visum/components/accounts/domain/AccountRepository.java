package znu.visum.components.accounts.domain;

import java.util.Optional;

public interface AccountRepository {

  long count();

  Account save(TransientAccount account);

  Optional<Account> findByUsername(String username);
}
