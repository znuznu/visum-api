package znu.visum.components.accounts.domain.models;

public class AccountToRegister {
  private String username;
  private String password;
  private String registrationKey;

  public AccountToRegister(String username, String password, String registrationKey) {
    this.username = username;
    this.password = password;
    this.registrationKey = registrationKey;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRegistrationKey() {
    return registrationKey;
  }

  public void setRegistrationKey(String registrationKey) {
    this.registrationKey = registrationKey;
  }
}
