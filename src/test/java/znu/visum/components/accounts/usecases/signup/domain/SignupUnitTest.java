package znu.visum.components.accounts.usecases.signup.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import znu.visum.components.accounts.domain.AccountRepository;
import znu.visum.components.accounts.domain.AccountToRegister;
import znu.visum.components.accounts.domain.InvalidRegistrationKeyException;
import znu.visum.components.accounts.domain.MaximumAccountReachedException;

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

  @Nested
  class Register {

    @Test
    @DisplayName("When the registration key is invalid, it should throw")
    void itShouldThrowRegistrationKeyRelatedError() {
      AccountToRegister accountToRegister =
          new AccountToRegister("th3g3ntl3man", "password", "wrong-key");

      assertThatThrownBy(() -> signup.process(accountToRegister))
          .isInstanceOf(InvalidRegistrationKeyException.class)
          .hasMessage("Invalid registration key.");
    }

    @Test
    @DisplayName("When there is already one account, it should throw")
    void whenThereIsAnAccount_itShouldThrow() {
      Mockito.when(accountRepository.count()).thenReturn(1L);

      AccountToRegister accountToRegister =
          new AccountToRegister("th3g3ntl3man", "password", "right-key");

      assertThatThrownBy(() -> signup.process(accountToRegister))
          .isInstanceOf(MaximumAccountReachedException.class)
          .hasMessage("Number of maximum account reached.");
    }

    @Test
    @DisplayName(
        "When the account is valid and the maximum number of account is not reached, it should not throw")
    void whenThereIsAnAccount_itShouldNotThrow() {
      Mockito.when(accountRepository.count()).thenReturn(0L);

      AccountToRegister accountToRegister =
          new AccountToRegister("th3g3ntl3man", "password", "right-key");

      signup.process(accountToRegister);
    }
  }
}
