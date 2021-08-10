package znu.visum.business.accounts.models;

public class AccountRegistration extends Account {
    private String registrationKey;
    private String confirmPassword;

    public AccountRegistration() {
        super();
    }

    public AccountRegistration(String username, String password, String confirmPassword, String registrationKey) {
        super(username, password);
        this.registrationKey = registrationKey;
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getRegistrationKey() {
        return registrationKey;
    }

    public void setRegistrationKey(String registrationKey) {
        this.registrationKey = registrationKey;
    }

    public Account toAccount() {
        return new Account(this.username, this.password);
    }
}
