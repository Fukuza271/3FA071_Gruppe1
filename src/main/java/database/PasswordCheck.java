package database;

import at.favre.lib.crypto.bcrypt.BCrypt;
import database.daos.UserDao;
import database.entities.User;

import java.util.ArrayList;
import java.util.List;

public class PasswordCheck {
    private static final UserDao userDao = new UserDao();

    public static boolean login(String usernameSubmitted, String passwordSubmitted) {
        List<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("username", "=", usernameSubmitted, ""));

        if (!userDao.where(conditions).isEmpty()) {
            User user = userDao.where(conditions).getFirst();
            String passwordEnc = user.getPassword();

            BCrypt.Result result = BCrypt.verifyer().verify(passwordSubmitted.toCharArray(), passwordEnc);

            return result.verified;
        }

        return false;
    }
}
