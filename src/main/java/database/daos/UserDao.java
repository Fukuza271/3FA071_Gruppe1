package database.daos;

import database.Condition;
import database.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDao extends DataAccessObject<User>{
    String sqlSelectUser = "SELECT id, username, password FROM users ";

    @Override
    User findById(UUID id) {
        return this.findById(sqlSelectUser + """
                WHERE id = ?;
                """, id, this::createUserEntity);
    }

    @Override
    public List<User> findAll() {
        return this.get(sqlSelectUser + """
                ;
                """, this::createUserEntity);
    }

    @Override
    boolean insert(User entity) {
        return this.insert("""
                INSERT INTO users (id, username, password)
                values(?,?,?)
                ON DUPLICATE KEY UPDATE username = ?
                                        password = ?;""", (PreparedStatement statement) -> {
            statement.setString(1, entity.getId().toString());
            statement.setString(2,entity.getUsername());
            statement.setString(3,entity.getPassword());
        });
    }

    @Override
    boolean update(User entity) {
        return this.update("""
                UPDATE users
                SET username = ?,
                password = ?""", (PreparedStatement statement) -> {
            statement.setString(1,entity.getUsername());
            statement.setString(2,entity.getPassword());
                }
                );
    }

    @Override
    boolean deleteById(UUID id) {
        return this.deleteById("DELETE FROM users WHERE id = ?", id);
    }

    @Override
    public List<User> where(List<Condition> argList) {
        StringBuilder builder = new StringBuilder();
        List<String> valueList = new ArrayList<>();
        String baseSql = """
                """ + sqlSelectUser + """
                WHERE\s
                """;
        for (Condition condition : argList) {
            builder.append(condition.getColumn()).
                    append(" ").
                    append(condition.getOperator()).
                    append(" ?");
            builder.append(" ").append(condition.getLogicalOperator()).append(" ");
            valueList.add(condition.getValue());
        }
        builder.append(";");
        String sql = baseSql + builder;
        return this.get(sql, this::createUserEntity, valueList);
    }



    private User createUserEntity(ResultSet rs){
        User user = null;
        try{
            user = new User(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("username"), rs.getString("password")
                    );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

}
