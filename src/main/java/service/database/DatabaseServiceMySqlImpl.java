package service.database;

import entity.account.User;
import entity.resource.DatabaseSettings;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import service.database.dao.UserDAO;
import service.database.jdbc.executor.TExecutor;

import java.util.List;

public class DatabaseServiceMySqlImpl implements DatabaseService {
    private SessionFactory sessionFactory;
    private TExecutor tExecutor;

    public DatabaseServiceMySqlImpl(DatabaseSettings databaseSettings) {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(User.class);
        configuration.setProperty("hibernate.dialect", databaseSettings.getDialect());
        configuration.setProperty("hibernate.connection.driver_class", databaseSettings.getDriver());
        configuration.setProperty("hibernate.connection.url", configureConnectionUrl(databaseSettings));
        configuration.setProperty("hibernate.connection.username", databaseSettings.getUser());
        configuration.setProperty("hibernate.connection.password", databaseSettings.getPassword());
        configuration.setProperty("hibernate.show_sql", databaseSettings.getShowSql());
        configuration.setProperty("hibernate.hbm2ddl.auto", databaseSettings.getMode());

        sessionFactory = createSessionFactory(configuration);
        tExecutor = new TExecutor(sessionFactory);
    }

    private String configureConnectionUrl(DatabaseSettings dbSettings) {
        return dbSettings.getType() +
                dbSettings.getHost() +
                ':' +
                dbSettings.getPort() +
                '/' +
                dbSettings.getSchema() +
                "?user=" +
                dbSettings.getUser() +
                "&password=" +
                dbSettings.getPassword() +
                "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    }

    @Override
    public void save(User dataSet) {
        tExecutor.execUpdate(
                (session, parameter) -> {
                    UserDAO dao = new UserDAO(session);
                    dao.save(dataSet);
                }, dataSet);
    }

    @Override
    public User read(long id) {
        return tExecutor.execQuery(
                (session, parameter) -> {
                    UserDAO dao = new UserDAO(session);

                    return dao.read(parameter);
                }, id);
    }

    @Override
    public User readByName(String name) {
        return tExecutor.execQuery(
                (session, parameter) -> {
                    UserDAO dao = new UserDAO(session);

                    return dao.readByName(parameter);
                }, name);
    }

    @Override
    public List<User> readAll() {
        return tExecutor.execQuery(
                (session, parameter) -> {
                    UserDAO dao = new UserDAO(session);

                    return dao.readAll();
                }, null);
    }

    @Override
    public long readCountAll() {
        return tExecutor.execQuery(
                (session, parameter) -> {
                    UserDAO dao = new UserDAO(session);

                    return dao.readCountAll();
                }, null);
    }

    @Override
    public void update(User dataSet) {
        tExecutor.execUpdate(
                (session, parameter) -> {
                    UserDAO dao = new UserDAO(session);
                    dao.update(dataSet);
                }, dataSet);
    }

    @Override
    public List<User> readLimitOrder(int limit) {
        Session session = sessionFactory.openSession();
        UserDAO dao = new UserDAO(session);

        return dao.readLimitOrder(limit);
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();

        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}
