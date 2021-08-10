package znu.visum.business.accounts.persistence.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import znu.visum.business.accounts.models.Account;
import znu.visum.business.accounts.persistence.AccountDao;

@Repository
public interface DataJpaAccountDao extends
        AccountDao,
        JpaRepository<Account, Long>,
        JpaSpecificationExecutor<Account> {
}
