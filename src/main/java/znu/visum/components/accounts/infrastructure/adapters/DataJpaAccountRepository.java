package znu.visum.components.accounts.infrastructure.adapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import znu.visum.components.accounts.infrastructure.models.AccountEntity;

import java.util.Optional;

@Repository
public interface DataJpaAccountRepository extends JpaRepository<AccountEntity, Long> {
  Optional<AccountEntity> findByUsername(String username);
}
