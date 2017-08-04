package service.database.jpa.dao;

import entity.account.User;
import entity.resource.DatabaseSettings;
import main.ResourceFactory;
import org.junit.Before;
import org.junit.Test;
import service.database.DAOFactory;
import service.database.dao.UserDAO;

import static org.junit.Assert.assertNotNull;

public class UserDAOImplTest {
    private UserDAO userDAO;
    @Before
    public void setUp() {
//        ResourceFactory.getInstance().loadAllResources("src/main/resources");
        ResourceFactory.getInstance().loadResource("src/main/resources/mysql.properties");
        DatabaseSettings databaseSettings = (DatabaseSettings) ResourceFactory.getInstance().loadResource("DatabaseSettings");
        assert databaseSettings != null;
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.JPA_MYSQL, databaseSettings);
        assert daoFactory != null;
        userDAO = daoFactory.getUserDAO();
    }

    @Test
    public void getByLogin() {
        User user = userDAO.getByLogin("asdf");
        assertNotNull(user);
    }

}