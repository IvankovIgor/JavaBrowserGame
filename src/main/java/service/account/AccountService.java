package service.account;

import entity.account.AccountStatus;

import java.util.Set;

public interface AccountService {
    Set<AccountStatus> signUp(String login, String password, String email);
    Set<AccountStatus> signIn(String login, String password);
}
