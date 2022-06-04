package znu.visum.components.accounts.infrastructure.models;

import lombok.Getter;
import znu.visum.components.accounts.domain.models.Account;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Getter
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
}
