package service.database.jpa;

import entity.resource.DatabaseSettings;
import main.ResourceSingleton;
import org.junit.Before;
import org.junit.Test;
import service.database.DAOFactory;

public class JpaMySqlDAOFactoryTest {

    @Before
    public void setUp() {
        DatabaseSettings databaseSettings = (DatabaseSettings) ResourceSingleton.getInstance().getResource(ResourceSingleton.MYSQL_PROPERTIES);
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.JPA_MYSQL, databaseSettings);
    }

    @Test
    public void getUserDAO() {
    }
}