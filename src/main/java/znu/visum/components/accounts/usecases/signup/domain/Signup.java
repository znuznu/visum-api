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
import znu.visum.components.accounts.domain.*;

import java.util.ArrayList;

@Service
@Slf4j
public class Signup implements UserDetailsService {

  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final String registrationKey;

  @Autowired
  public Signup(
      @Value("${visum.registration-key}") String registrationKey,
      AccountRepository accountRepository,
      PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
    this.registrationKey = registrationKey;
  }

  public void process(AccountToRegister accountToRegister) {
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

  private Account create(Account accountEntity) {
    accountEntity.setPassword(passwordEncoder.encode(accountEntity.getPassword()));

    return this.accountRepository.save(accountEntity);
  }

  private void validateMaximumAccounts() {
    // TODO should probably be a config property
    if (this.count() != 0) {
      log.error("Trying to add a new account.");
      throw new MaximumAccountReachedException();
    }
  }

  private long count() {
    return this.accountRepository.count();
  }

  private void validateRegistrationKey(
      AccountToRegister accountToRegister, String registrationKey) {
    if (!accountToRegister.getRegistrationKey().equals(registrationKey)) {
      throw new InvalidRegistrationKeyException();
    }
  }
}
