package service.database.jdbc.dao;

import entity.account.User;
import entity.resource.DatabaseSettings;
import main.ResourceFactory;
import org.junit.Before;
import org.junit.Test;
import service.database.DatabaseService;
import service.database.jdbc.DatabaseServiceMySQLImpl;

import static org.junit.Assert.assertEquals;

public class UserDAOTest {
    private UserDAO userDAO;
    private DatabaseService databaseService;

    @Before
    public void setUp() {
        ResourceFactory.getInstance().loadAllResources("src/main/res");
        DatabaseSettings databaseSettings = (DatabaseSettings) ResourceFactory.getInstance().getResource("DatabaseSettings");
        databaseService = new DatabaseServiceMySQLImpl(databaseSettings);
    }

    @Test
    public void save() throws Exception {
        User newUser = new User(3L, "asdf", "qqqq", "tttt");
        databaseService.save(newUser);
        User userFromDatabase = databaseService.read(3);
        assertEquals(newUser.getId(), userFromDatabase.getId());
        assertEquals(newUser.getLogin(), userFromDatabase.getLogin());
        assertEquals(newUser.getPassword(), userFromDatabase.getPassword());
        assertEquals(newUser.getEmail(), userFromDatabase.getEmail());
    }

    @Test
    public void get() throws Exception {
    }

}