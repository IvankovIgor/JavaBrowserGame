package service.database.jdbc.executor;

import org.hibernate.Session;

public interface ExecUpdate<P> {
    void execUpdate(Session session, P parameter);
}