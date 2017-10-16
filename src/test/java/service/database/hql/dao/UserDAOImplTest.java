package service.database.hql.dao;

import entity.account.User;
import entity.resource.DatabaseSettings;
import main.ResourceSingleton;
import org.junit.BeforeClass;
import org.junit.Test;
import service.database.DAOFactory;
import service.database.dao.UserDAO;

import static org.junit.Assert.assertEquals;

public class UserDAOImplTest {
    private static UserDAO userDAO;

    @BeforeClass
    public static void setUp() {
        DatabaseSettings databaseSettings = (DatabaseSettings) ResourceSingleton.getInstance().getResource(ResourceSingleton.POSTGRES_TEST_PROPERTIES);
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.HQL, databaseSettings);
        assert daoFactory != null;
        userDAO = daoFactory.getUserDAO();
    }

    @Test
    public void getByLogin() {
        User expected = new User("asdf", "qqqq", "tttt");
        int result = userDAO.save(expected);
        User actual = userDAO.getByLogin("asdf");
        assertEquals(1, result);
        assertEquals(expected.getLogin(), actual.getLogin());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getEmail(), actual.getEmail());
    }

    @Test
    public void get() {
        User expected = new User("asdf", "qqqq", "tttt");
        userDAO.save(expected);
        User actual = userDAO.get(1);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getLogin(), actual.getLogin());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getEmail(), actual.getEmail());
    }
//
//    @Test
//    public void save() throws Exception {
//        User newUser = new User("asdf", "qqqq", "tttt");
//
//        int actual = userDAO.save(newUser);
//
//        int expected = 1;
//        assertEquals(expected, actual);
//    }
}