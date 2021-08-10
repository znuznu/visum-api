package znu.visum.business.accounts.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import znu.visum.business.accounts.models.Account;
import znu.visum.business.accounts.models.AccountRegistration;
import znu.visum.business.accounts.persistence.AccountDao;
import znu.visum.business.accounts.services.AccountService;
import znu.visum.core.exception.ControllerException;
import znu.visum.core.exception.ExceptionEntity;

import java.util.ArrayList;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {
    private final AccountDao accountDao;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Value("${visum.registration-key}")
    private String registrationKey;

    @Autowired
    public AccountServiceImpl(AccountDao accountDao, PasswordEncoder passwordEncoder) {
        this.accountDao = accountDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public long count() {
        return this.accountDao.count();
    }

    @Override
    public void deleteById(long id) {
        this.accountDao.deleteById(id);
    }

    @Override
    public Account create(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        return this.accountDao.save(account);
    }

    @Override
    public void validatePasswords(AccountRegistration accountRegistration) {
        if (!accountRegistration.getConfirmPassword().equals(accountRegistration.getPassword())) {
            throw new ControllerException(new ExceptionEntity.Builder()
                    .message("Confirmation password doesn't match the password.")
                    .status(HttpStatus.CONFLICT)
                    .code("409")
                    .path("/api/accounts")
                    .error("Conflict")
                    .build()
            );
        }
    }

    @Override
    public void validateRegistrationKey(AccountRegistration accountRegistration) {
        if (!accountRegistration.getRegistrationKey().equals(registrationKey)) {
            throw new ControllerException(new ExceptionEntity.Builder()
                    .message("Invalid registration key.")
                    .status(HttpStatus.UNAUTHORIZED)
                    .code("401")
                    .path("/api/accounts")
                    .error("Unauthorized")
                    .build()
            );
        }
    }

    @Override
    public void validateMaximumAccounts(AccountRegistration accountRegistration) {
        if (this.count() != 0) {
            logger.error("Trying to add a new account.");
            throw new ControllerException(new ExceptionEntity.Builder()
                    .message("Maximum accounts number reached.")
                    .status(HttpStatus.FORBIDDEN)
                    .code("403")
                    .path("/api/accounts")
                    .error("Forbidden")
                    .build()
            );
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.accountDao.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        return new User(account.getUsername(), account.getPassword(), new ArrayList<>());
    }
}
