package znu.visum.account.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import znu.visum.business.accounts.models.AccountRegistration;
import znu.visum.business.accounts.persistence.AccountDao;
import znu.visum.business.accounts.services.impl.AccountServiceImpl;
import znu.visum.core.exception.ControllerException;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = AccountServiceImpl.class)
@ExtendWith(SpringExtension.class)
@DisplayName("class AccountServiceImpl")
@TestPropertySource(properties = {
        "visum.registration-key=my_super_key",
})
public class AccountServiceImplTest {
    AccountRegistration accountRegistration;

    @MockBean
    private AccountDao accountDao;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @Nested
    @DisplayName("method validatePasswords()")
    class ValidatePasswords {

        @Test
        void shouldThrowPasswordMessageWhenPasswordsAreDifferent() {
            accountRegistration = new AccountRegistration("th3g3ntl3man", "password1", "password2", "my-registration-key");

            Exception exception = assertThrows(ControllerException.class, () ->
                    accountServiceImpl.validatePasswords(accountRegistration)
            );

            String expectedMessage = "Confirmation password doesn't match the password.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }

        @Test
        void shouldNotThrowPasswordMessageWhenPasswordsAreIdentical() {
            accountRegistration = new AccountRegistration("th3g3ntl3man", "password1234", "password1234", "my-registration-key");
            assertDoesNotThrow(() -> accountServiceImpl.validatePasswords(accountRegistration));
        }
    }

    @Nested
    @DisplayName("method validateRegistrationKey()")
    class Validate {

        @Test
        void shouldThrowRegistrationKeyMessageWhenRegistreykeyIsDifferent() {
            accountRegistration = new AccountRegistration("th3g3ntl3man", "password", "password", "something_wrong");

            Exception exception = assertThrows(ControllerException.class, () ->
                    accountServiceImpl.validateRegistrationKey(accountRegistration)
            );

            String expectedMessage = "Invalid registration key.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }

        @Test
        void shouldNotThrowRegistrationKeyMessageWhenRegistrykeyIsIdentical() {
            accountRegistration = new AccountRegistration("th3g3ntl3man", "password1234", "password1234", "my_super_key");
            assertDoesNotThrow(() -> accountServiceImpl.validateRegistrationKey(accountRegistration));
        }
    }

    @Nested
    @DisplayName("method validateMaximumAccounts()")
    class ValidateMaximumAccounts {

        @Test
        void shouldThrowMaximumAccountsMessageWhenThereIsOneAccount() {
            Mockito.when(accountDao.count()).thenReturn(1L);

            accountRegistration = new AccountRegistration("th3g3ntl3man", "password", "password", "my_super_key");

            Exception exception = assertThrows(ControllerException.class, () ->
                    accountServiceImpl.validateMaximumAccounts(accountRegistration)
            );

            String expectedMessage = "Maximum accounts number reached.";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        }

        @Test
        void shouldNotThrowMaximumAccountsMessageWhenThereIsNoAccount() {
            Mockito.when(accountDao.count()).thenReturn(0L);

            accountRegistration = new AccountRegistration("th3g3ntl3man", "password1234", "password1234", "my_super_key");
            assertDoesNotThrow(() -> accountServiceImpl.validateMaximumAccounts(accountRegistration));
        }
    }
}
