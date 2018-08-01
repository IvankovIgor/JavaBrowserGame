package util;

import entity.account.AccountStatus;
import service.database.dao.UserDAO;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Igor Ivankov
 */
public class AccountValidator {
    private final Set<AccountStatus> accountStatuses;

    private final UserDAO userDAO;
    private final boolean isSignUp;

    public AccountValidator(UserDAO userDAO, Set<AccountStatus> accountStatuses, boolean isSignUp) {
        this.accountStatuses = accountStatuses;
        this.userDAO = userDAO;
        this.isSignUp = isSignUp;
    }

    public void validate(String login, String password) {
        validateLogin(login);
        validatePassword(password);
    }

    public void validate(String login, String password, String email) {
        validateLogin(login);
        validatePassword(password);
        validateEmail(email);
    }

    private void validateLogin(String login) {
        if (isEmptyLogin(login)) {
            accountStatuses.add(AccountStatus.EMPTY_LOGIN);
        } else if (isInvalidLogin(login)) {
            accountStatuses.add(AccountStatus.INVALID_LOGIN);
        } else if (isSignUp && isExistingLogin(login)) {
            accountStatuses.add(AccountStatus.EXISTING_LOGIN);
        }
    }

    private boolean isEmptyLogin(String login) {
        return login == null || login.isEmpty();
    }

    private boolean isInvalidLogin(String login) {
        Pattern pattern = Pattern.compile("\\w{3,15}");
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    private boolean isExistingLogin(String login) {
        return userDAO.getByLogin(login) != null;
    }

    private void validatePassword(String password) {
        if (isEmptyPassword(password)) {
            accountStatuses.add(AccountStatus.EMPTY_PASSWORD);
        } else if (isInvalidPassword(password)) {
            accountStatuses.add(AccountStatus.INVALID_PASSWORD);
        }
    }

    private boolean isEmptyPassword(String password) {
        return password == null || password.isEmpty();
    }

    private boolean isInvalidPassword(String password) {
        Pattern pattern = Pattern.compile(".{8,20}");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private void validateEmail(String email) {
        if (isEmptyEmail(email)) {
            accountStatuses.add(AccountStatus.EMPTY_EMAIL);
        } else if (isInvalidEmail(email)) {
            accountStatuses.add(AccountStatus.INVALID_EMAIL);
        } else if (isSignUp && isExistingEmail(email)) {
            accountStatuses.add(AccountStatus.EXISTING_EMAIL);
        }
    }

    private boolean isEmptyEmail(String email) {
        return email == null || email.isEmpty();
    }

    private boolean isInvalidEmail(String email) {
        return !email.contains("@");
    }

    private boolean isExistingEmail(String email) {
        return userDAO.getByEmail(email) != null;
    }

    public Set<AccountStatus> getAccountStatuses() {
        return accountStatuses;
    }
}
