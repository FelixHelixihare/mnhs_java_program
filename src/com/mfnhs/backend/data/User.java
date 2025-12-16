package com.mfnhs.backend.data;

import java.sql.Timestamp;

public class User {
    private int user_id;
    private Name user_name;

    public User(int user_id,
                String user_first_name,
                String user_last_name,
                String user_middle_name,
                String user_extension_name) {
        this.user_id = user_id;
        this.user_name = new Name(user_first_name, user_last_name, user_middle_name, user_extension_name);
    }

    public String get_first_name() {return user_name.first_name;}
    public String get_last_name() {return user_name.last_name;}
    public String get_middle_name() {return user_name.middle_name;}
    public String get_middle_initial() {return user_name.get_middle_initial();}
    public String get_extension_name() {return user_name.extension_name;}

    public String get_lfm_name() {return user_name.get_lfm_name();}
    public String get_fml_name() {return user_name.get_fml_name();}
    public String get_full_name() {return user_name.get_full_name();}
}
