package service.database.jdbc.handler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatamentHandler {
    void handle(PreparedStatement stmt) throws SQLException;
}
