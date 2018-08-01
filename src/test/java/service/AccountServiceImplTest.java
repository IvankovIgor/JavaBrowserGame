package service;

import entity.account.AccountStatus;
import entity.resource.DatabaseSettings;
import main.Context;
import main.ResourceSingleton;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.database.DAOFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 * @author Igor Ivankov
 */

@RunWith(Parameterized.class)
public class AccountServiceImplTest {
    private static AccountService accountService;
    private String login;
    private String password;
    private String email;
    private AccountStatus[] expectedAccountStatuses;

    @BeforeClass
    public static void setUp() {
        Context context = new Context();
        DatabaseSettings databaseSettings = (DatabaseSettings) ResourceSingleton.getInstance().getResource(ResourceSingleton.MYSQL_TEST_PROPERTIES);
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.JPA_MYSQL, databaseSettings);
        context.add(DAOFactory.class, daoFactory);
        accountService = new AccountServiceImpl(daoFactory);
    }

    public AccountServiceImplTest(Object[] input, Object[] expectedStatuses) {
        this.login = (String) input[0];
        this.password = (String) input[1];
        this.email = (String) input[2];
        this.expectedAccountStatuses = Arrays.copyOf(expectedStatuses, expectedStatuses.length, AccountStatus[].class);
    }

    @Parameterized.Parameters
    public static Collection inputs() {
        Object[][][] objects = {
                {{"admin", "adminadmin", "ololo@mail.ru"}, {AccountStatus.CREATED}},
                {{"admin", "admin", "ololo@mail.ru"}, {AccountStatus.EXISTING_LOGIN, AccountStatus.TOO_SHORT_PASSWORD}},
                {{"", "adminshaasdffd", "ololo@mail.ru"}, {AccountStatus.EMPTY_LOGIN}},
                {{"ololosha", "fucjiasdf", "privetmail.ru"}, {AccountStatus.INVALID_EMAIL}}
        };
        return Arrays.asList(objects);
    }

    @Test
    public void testSignUp() throws Exception {
        Set<AccountStatus> actualAccountStatuses = accountService.signUp(login, password, email);
        assertThat(actualAccountStatuses, containsInAnyOrder(expectedAccountStatuses));
    }
}