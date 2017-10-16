package service.database.jpa.dao;

import entity.account.User;
import entity.resource.DatabaseSettings;
import main.ResourceSingleton;
import org.junit.BeforeClass;
import org.junit.Test;
import service.database.DAOFactory;
import service.database.dao.UserDAO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserDAOImplTest {
    private static UserDAO userDAO;

    @BeforeClass
    public static void setUp() {
        DatabaseSettings databaseSettings = (DatabaseSettings) ResourceSingleton.getInstance().getResource(ResourceSingleton.MYSQL_TEST_PROPERTIES);
        assert databaseSettings != null;
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.JPA_MYSQL, databaseSettings);
        assert daoFactory != null;
        userDAO = daoFactory.getUserDAO();
    }

    @Test
    public void save() {
        User expected = new User("qwer", "qwer", "qwer@mai.ru");
        userDAO.save(expected);
        User actual = userDAO.getByLogin("qwer");
        assertEquals(expected.getLogin(), actual.getLogin());
    }

    @Test
    public void getByLogin() {
        User user = userDAO.getByLogin("qwer");
        assertNotNull(user);
    }

}