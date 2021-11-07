package znu.visum.components.accounts.infrastructure.models;

import znu.visum.components.accounts.domain.models.Account;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class AccountEntity {
  @Column(unique = true)
  protected String username;

  protected String password;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
  private Long id;

  public AccountEntity() {}

  public AccountEntity(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public static AccountEntity from(Account account) {
    return new AccountEntity(account.getUsername(), account.getPassword());
  }

  public Account toDomain() {
    return new Account(this.id, this.username, this.password);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
}
