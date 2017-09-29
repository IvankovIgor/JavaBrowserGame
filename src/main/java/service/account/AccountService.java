package service.account;

import entity.account.AccountStatus;
import messagesystem.Abonent;

import java.util.Set;

public interface AccountService extends Abonent {
    Set<AccountStatus> signUp(String login, String password, String email);
    Set<AccountStatus> signIn(String login, String password);
}
