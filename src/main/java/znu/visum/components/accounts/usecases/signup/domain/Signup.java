package znu.visum.components.accounts.usecases.signup.domain;

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

  public Account process(RegisterAccountCommand command) {
    this.validateRegistrationKey(command, registrationKey);
    this.validateMaximumAccounts();

    return this.create(command);
  }

  public UserDetails loadUserByUsername(String username) {
    Account account =
        this.accountRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

    return new User(account.username(), account.password(), new ArrayList<>());
  }

  private Account create(RegisterAccountCommand command) {
    var encodedPassword = passwordEncoder.encode(command.password());
    var account = new TransientAccount(command.username(), encodedPassword);

    return this.accountRepository.save(account);
  }

  private void validateMaximumAccounts() {
    // TODO should probably be a config property
    if (this.count() != 0) {
      throw new MaximumAccountReachedException();
    }
  }

  private long count() {
    return this.accountRepository.count();
  }

  private void validateRegistrationKey(
      RegisterAccountCommand registerAccountCommand, String registrationKey) {
    if (!registerAccountCommand.registrationKey().equals(registrationKey)) {
      throw new InvalidRegistrationKeyException();
    }
  }
}
