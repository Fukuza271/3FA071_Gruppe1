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

            if(passwordEnc.equals(encrypt(passwordSubmitted))){

            }
        }
        return false;
    }

    private String encrypt(String pw){
        String bcryptHashString = BCrypt.withDefaults().hashToString(12,pw.toCharArray());
        // $2a$12$US00g/uMhoSBm.HiuieBjeMtoN69SN.GE25fCpldebzkryUyopws6

        BCrypt.Result result = BCrypt.verifyer().verify(pw.toCharArray(), bcryptHashString);
        System.out.print(result.verified);
        return result.toString();
    }
}
