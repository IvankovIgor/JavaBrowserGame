package service.database.jpa;

import entity.resource.DatabaseSettings;
import main.ResourceFactory;
import org.junit.Before;
import org.junit.Test;
import service.database.DAOFactory;

public class JpaMySqlDAOFactoryTest {
    @Before
    public void setUp() {
//        ResourceFactory.getInstance().loadAllResources("src/main/resources");
        ResourceFactory.getInstance().loadResource("src/main/resources/mysql.properties");
        DatabaseSettings databaseSettings = (DatabaseSettings) ResourceFactory.getInstance().loadResource("DatabaseSettings");
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.JPA_MYSQL, databaseSettings);
    }

    @Test
    public void getUserDAO() {
        //TODO
    }
}