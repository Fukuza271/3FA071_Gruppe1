package interfaces;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface AddParamsToStatement {
    void execute(PreparedStatement statement) throws SQLException;
}
