package part2.lesson17.db.ConnectionManager;

import java.sql.Connection;

public interface ConnectionManager {
    /**
     * ПОлучить экземпляр соединения
     * @return Connection
     */
    public Connection getConnection();
}
