package znu.visum.components.accounts.usecases.signup.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import znu.visum.components.accounts.domain.models.Account;
import znu.visum.components.accounts.domain.models.AccountToRegister;
import znu.visum.components.accounts.domain.ports.AccountRepository;

import java.util.ArrayList;

@Service
public class SignupServiceImpl implements SignupService, UserDetailsService {
  private final AccountRepository accountRepository;
  private final PasswordEncoder passwordEncoder;
  private final String registrationKey;

  @Autowired
  public SignupServiceImpl(
      @Value("${visum.registration-key}") String registrationKey,
      AccountRepository accountRepository,
      PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
    this.registrationKey = registrationKey;
  }

  @Override
  public long count() {
    return this.accountRepository.count();
  }

  @Override
  public Account create(Account accountEntity) {
    accountEntity.setPassword(passwordEncoder.encode(accountEntity.getPassword()));

    return this.accountRepository.save(accountEntity);
  }

  @Override
  public void register(AccountToRegister accountToRegister) {
    this.validateRegistrationKey(accountToRegister, registrationKey);
    this.validateMaximumAccounts();
    this.create(
        new Account(null, accountToRegister.getUsername(), accountToRegister.getPassword()));
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    Account account =
        this.accountRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

    return new User(account.getUsername(), account.getPassword(), new ArrayList<>());
  }
}
