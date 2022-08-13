package znu.visum.components.accounts.usecases.signup.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import znu.visum.components.accounts.domain.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SignupUnitTest {

  @Mock private AccountRepository accountRepository;

  @Mock private PasswordEncoder passwordEncoder;

  private Signup signup;

  @BeforeEach
  void setup() {
    this.signup = new Signup("right-key", accountRepository, passwordEncoder);
  }

  @Test
  void shouldNotCreateWithoutValidRegistrationKey() {
    RegisterAccountCommand registerAccountCommand =
        new RegisterAccountCommand("th3g3ntl3man", "password", "wrong-key");

    assertThatThrownBy(() -> signup.process(registerAccountCommand))
        .isInstanceOf(InvalidRegistrationKeyException.class)
        .hasMessage("Invalid registration key.");
  }

  @Test
  void shouldNotCreateIfAnAccountAlreadyExists() {
    Mockito.when(accountRepository.count()).thenReturn(1L);

    RegisterAccountCommand registerAccountCommand =
        new RegisterAccountCommand("th3g3ntl3man", "password", "right-key");

    assertThatThrownBy(() -> signup.process(registerAccountCommand))
        .isInstanceOf(MaximumAccountReachedException.class)
        .hasMessage("Number of maximum account reached.");
  }

  @Test
  void shouldCreateIfNoAccountExists() {
    Mockito.when(accountRepository.count()).thenReturn(0L);
    Mockito.when(passwordEncoder.encode("password")).thenReturn("super_hash");
    Mockito.when(accountRepository.save(new TransientAccount("th3g3ntl3man", "super_hash")))
        .thenReturn(new Account(1L, "th3g3ntl3man", "super_hash"));

    RegisterAccountCommand command =
        new RegisterAccountCommand("th3g3ntl3man", "password", "right-key");

    var account = signup.process(command);

    assertThat(account).isEqualTo(new Account(1L, "th3g3ntl3man", "super_hash"));
  }
}
