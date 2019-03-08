package ie.cp.models;



import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

// This class holds user details

public class User extends RealmObject
 {
    @PrimaryKey
    public String userId;
    public String userName;
    public String emailAddress;
    public String password;

    public User() {}

    public User(String userName, String emailAddress, String password)
    {
        this.userId = UUID.randomUUID().toString();
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [userName=" + userName
                + ", emailAddress =" + emailAddress + ", password=" + password + "]";
    }
}

