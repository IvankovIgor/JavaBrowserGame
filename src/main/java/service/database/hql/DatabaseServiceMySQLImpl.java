package service.database.hql;

import entity.account.User;
import entity.resource.DatabaseSettings;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DatabaseServiceMySQLImpl {
        private SessionFactory sessionFactory;

    public DatabaseServiceMySQLImpl(DatabaseSettings databaseSettings) {
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

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();

        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}
