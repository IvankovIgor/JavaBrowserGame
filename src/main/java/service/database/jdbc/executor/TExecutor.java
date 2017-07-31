package service.database.jdbc.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.database.jdbc.handler.PreparedStatamentHandler;
import service.database.jdbc.handler.TResultHandler;

import java.lang.invoke.MethodHandles;
import java.sql.*;

public class TExecutor {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public <T> T execQuery(Connection connection, String query, TResultHandler<T> handler) throws SQLException {
        T value = null;

        try (Statement stmt = connection.createStatement()) {
            try (ResultSet result = stmt.executeQuery(query)) {
                value = handler.handle(result);
            } catch (SQLException e) {
                logger.warn("When executing query");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            logger.warn("When creating statement");
            e.printStackTrace();
        }

        return value;
    }

    public int execUpdate(Connection connection, String update) {
        int value = 0;

        try (Statement stmt = connection.createStatement()) {
            value = stmt.executeUpdate(update);
        } catch (SQLException e) {
            logger.warn("When creating statement");
            e.printStackTrace();
        }

        return value;
    }

    public int execPreparedUpdate(Connection connection, String update, PreparedStatamentHandler handler) {
        int value = 0;

        try (PreparedStatement stmt = connection.prepareStatement(update)) {
            handler.handle(stmt);
            value = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn("While executing prepared update");
        }

        return value;
    }
}
