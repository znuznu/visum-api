package znu.visum.components.accounts.usecases.signup.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import znu.visum.components.accounts.domain.errors.InvalidRegistrationKeyException;
import znu.visum.components.accounts.domain.errors.MaximumAccountReachedException;
import znu.visum.components.accounts.domain.models.Account;
import znu.visum.components.accounts.domain.models.AccountToRegister;
import znu.visum.components.accounts.domain.ports.AccountRepository;

import java.util.ArrayList;

@Service
@Slf4j
public class SignupService implements UserDetailsService {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final String registrationKey;

  @Autowired
  public SignupService(
      @Value("${visum.registration-key}") String registrationKey,
      AccountRepository accountRepository,
      PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
    this.registrationKey = registrationKey;
  }

  public long count() {
    return this.accountRepository.count();
  }

  public Account create(Account accountEntity) {
    accountEntity.setPassword(passwordEncoder.encode(accountEntity.getPassword()));

    return this.accountRepository.save(accountEntity);
  }

  public void register(AccountToRegister accountToRegister) {
    this.validateRegistrationKey(accountToRegister, registrationKey);
    this.validateMaximumAccounts();
    this.create(
        new Account(null, accountToRegister.getUsername(), accountToRegister.getPassword()));
  }

  public UserDetails loadUserByUsername(String username) {
    Account account =
        this.accountRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

    return new User(account.getUsername(), account.getPassword(), new ArrayList<>());
  }

  void validateMaximumAccounts() {
    // TODO should probably be a config property
    if (this.count() != 0) {
      log.error("Trying to add a new account.");
      throw new MaximumAccountReachedException();
    }
  }

  void validateRegistrationKey(AccountToRegister accountToRegister, String registrationKey) {
    if (!accountToRegister.getRegistrationKey().equals(registrationKey)) {
      throw new InvalidRegistrationKeyException();
    }
  }
}
