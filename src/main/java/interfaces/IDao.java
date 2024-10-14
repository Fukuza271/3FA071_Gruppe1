package interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface IDao<T> {
    public T findById(UUID id);
    public List<T> findAll() throws SQLException;
    public boolean insert(T entity);
    public boolean update(T entity);
    public boolean deleteById(UUID id);
}
