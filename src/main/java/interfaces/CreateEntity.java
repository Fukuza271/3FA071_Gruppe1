package interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface CreateEntity<T> {
    T execute(ResultSet rs) throws SQLException;
}
