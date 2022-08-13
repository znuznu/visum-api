package znu.visum.components.accounts.domain;

public record RegisterAccountCommand(String username, String password, String registrationKey) {
}
