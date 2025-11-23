import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class User {
    private int user_id;
    private String user_first_name;
    private String user_last_name;
    private String user_middle_name;
    private String user_extension_name;
    private Date user_birthdate;
    private Timestamp user_dateCreated;
    private String user_password;

    public User(int user_id,
                String user_first_name,
                String user_last_name,
                String user_middle_name,
                String user_extension_name,
                Date user_birthdate,
                Timestamp user_dateCreated,
                String user_password) {
        this.user_id = user_id;
        this.user_first_name = user_first_name;
        this.user_last_name = user_last_name;
        this.user_middle_name = user_middle_name;
        this.user_extension_name = user_extension_name;
        this.user_birthdate = user_birthdate;
        this.user_dateCreated = user_dateCreated;
        this.user_password = user_password;
    }

    public String getUser_first_name() {return user_first_name;}
    public String getUser_last_name() {return user_last_name;}
    public String getUser_middle_name() {return user_middle_name;}
    public String getUser_middle_initial() {return user_middle_name.substring(0, 1);}
    public String getUser_extension_name() {return user_extension_name;}
    public String getUser_birthdate() {return user_birthdate.toString();}
    public String getUser_dateCreated() {return user_dateCreated.toString();}
}
