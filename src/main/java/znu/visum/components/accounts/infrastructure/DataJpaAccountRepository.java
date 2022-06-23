package znu.visum.components.accounts.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataJpaAccountRepository extends JpaRepository<AccountEntity, Long> {
  Optional<AccountEntity> findByUsername(String username);
}
