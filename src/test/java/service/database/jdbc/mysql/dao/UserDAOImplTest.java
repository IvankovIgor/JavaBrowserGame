package service.database.jdbc.mysql.dao;

import entity.account.User;
import entity.resource.DatabaseSettings;
import main.ResourceFactory;
import org.junit.Before;
import org.junit.Test;
import service.database.DAOFactory;
import service.database.dao.UserDAO;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class UserDAOImplTest {
    private UserDAO userDAO;

    @Before
    public void setUp() {
        ResourceFactory.getInstance().loadAllResources("src/main/resources");
        DatabaseSettings databaseSettings = (DatabaseSettings) ResourceFactory.getInstance().getResource("DatabaseSettings");
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.JDBC_MYSQL, databaseSettings);
        assert daoFactory != null;
        userDAO = daoFactory.getUserDAO();
    }

    @Test
    public void save() throws SQLException {
        User newUser = new User("asdf", "qqqq", "tttt");

        int actual = userDAO.save(newUser);

        int expected = 1;
        assertEquals(expected, actual);
//        User actual = userDAO.get(4);
//        assertEquals(expected.getId(), actual.getId());
//        assertEquals(expected.getLogin(), actual.getLogin());
//        assertEquals(expected.getPassword(), actual.getPassword());
//        assertEquals(expected.getEmail(), actual.getEmail());
    }

    @Test
    public void get() throws Exception {
//        User actual = userDAO.get(4);
//        assertEquals(expected.getId(), actual.getId());
//        assertEquals(expected.getLogin(), actual.getLogin());
//        assertEquals(expected.getPassword(), actual.getPassword());
//        assertEquals(expected.getEmail(), actual.getEmail());
    }

}