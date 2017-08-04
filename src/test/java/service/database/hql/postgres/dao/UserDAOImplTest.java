package service.database.hql.postgres.dao;

import entity.resource.DatabaseSettings;
import main.ResourceFactory;
import org.junit.Before;
import org.junit.Test;
import service.database.DAOFactory;
import service.database.dao.UserDAO;

public class UserDAOImplTest {
    UserDAO userDAO;

    @Before
    public void setUp() {
        ResourceFactory.getInstance().loadResource("src/main/resources/postgres.properties");
        DatabaseSettings databaseSettings = (DatabaseSettings) ResourceFactory.getInstance().loadResource("DatabaseSettings");
        assert databaseSettings != null;
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.HQL_POSTGRES, databaseSettings);
        assert daoFactory != null;
        userDAO = daoFactory.getUserDAO();
    }
    @Test
    public void save() throws Exception {
    }

    @Test
    public void get() throws Exception {
    }

    @Test
    public void getByLogin() throws Exception {
    }

}