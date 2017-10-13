package service.database.jpa;

import entity.resource.DatabaseSettings;
import main.ResourceSingleton;
import org.junit.Before;
import org.junit.Test;
import service.database.DAOFactory;

public class JpaMySqlDAOFactoryTest {

    @Before
    public void setUp() {
        ResourceSingleton.getInstance().loadResource("src/main/resources/mysql.properties");
        DatabaseSettings databaseSettings = (DatabaseSettings) ResourceSingleton.getInstance().getResource(DatabaseSettings.class);
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.JPA, databaseSettings);
    }

    @Test
    public void getUserDAO() {
    }
}