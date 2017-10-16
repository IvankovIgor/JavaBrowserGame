package util;

import entity.account.AccountStatus;
import entity.resource.DatabaseSettings;
import main.ResourceSingleton;
import org.junit.Before;
import org.junit.Test;
import service.database.DAOFactory;

import java.util.EnumSet;
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
        DatabaseSettings databaseSettings = (DatabaseSettings) ResourceSingleton.getInstance().getResource(ResourceSingleton.MYSQL_PROPERTIES);
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.JPA_MYSQL, databaseSettings);
        accountValidator = new AccountValidator(daoFactory.getUserDAO(), statuses);
        statuses = EnumSet.allOf(AccountStatus.class);
    }

    @Test
    public void testValidate() throws Exception {
        // TODO
        accountValidator.validate("admin", "adminadmin");
        assertTrue(true);
    }
}