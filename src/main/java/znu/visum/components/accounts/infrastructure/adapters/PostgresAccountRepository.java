package znu.visum.components.accounts.infrastructure.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import znu.visum.components.accounts.domain.models.Account;
import znu.visum.components.accounts.domain.ports.AccountRepository;
import znu.visum.components.accounts.infrastructure.models.AccountEntity;

import java.util.Optional;

@Repository
public class PostgresAccountRepository implements AccountRepository {
  private final DataJpaAccountRepository dataJpaAccountRepository;

  @Autowired
  public PostgresAccountRepository(DataJpaAccountRepository dataJpaAccountRepository) {
    this.dataJpaAccountRepository = dataJpaAccountRepository;
  }

  @Override
  public long count() {
    return this.dataJpaAccountRepository.count();
  }

  @Override
  public Account save(Account account) {
    return dataJpaAccountRepository.save(AccountEntity.from(account)).toDomain();
  }

  @Override
  public Optional<Account> findByUsername(String username) {
    return dataJpaAccountRepository.findByUsername(username).map(AccountEntity::toDomain);
  }
}
