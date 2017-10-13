package util;

import entity.account.AccountStatus;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by ivankov on 17.07.2017.
 */

public class AccountValidatorTest {
    private AccountValidator accountValidator;
    Set<AccountStatus> statuses;
    private String login;
    private String password;
    private String email;

    @Before
    public void setUp() {
        accountValidator = new AccountValidator(statuses);
        statuses = new HashSet<>();
    }

    @Test
    public void testValidate() throws Exception {
        // TODO
        accountValidator.validate("admin", "adminadmin");
        assertTrue(true);
    }
}