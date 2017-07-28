package service.database.jdbc.executor;

import org.hibernate.Session;

public interface ExecQuery<T, P> {
    T execQuery(Session session, P parameter);
}
