package interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface IDao<T> {
    public T findById(UUID id);
    public List<T> findAll() throws SQLException;
    public void insert(T entity);
    public void update(T entity);
    public void deleteById(UUID id);
}
