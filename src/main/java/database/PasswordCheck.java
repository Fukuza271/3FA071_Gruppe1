package database;

import at.favre.lib.crypto.bcrypt.BCrypt;
import database.daos.UserDao;
import database.entities.User;
import interfaces.IDatabaseConnection;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class PasswordCheck {
    IDatabaseConnection dbcon = new DatabaseConnection().openConnection(Property.readProperties());
    String passwordClear = "";
    String passwordSubmitted = "";
    String usernameSubmitted = "";
    String passwordEnc = "";

    public boolean PasswordCheck(String usernameSubmitted, String passwordSubmitted){
        this.usernameSubmitted = usernameSubmitted;
        this.passwordSubmitted = passwordSubmitted;
        List<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("username","=",usernameSubmitted,""));
        UserDao ud = new UserDao();
        if(!ud.where(conditions).isEmpty()){
            List res = ud.where(conditions);
            User user = (User) res.getFirst();
            passwordEnc = user.getPassword();

            //if()
        }
        return false;
    }

    private void decryptPW(){
        //passwordClear = ;
    }
}
