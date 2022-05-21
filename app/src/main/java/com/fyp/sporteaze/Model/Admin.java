package com.fyp.sporteaze.Model;

import com.google.firebase.database.IgnoreExtraProperties;
@IgnoreExtraProperties
public class Admin
{
    public String email, password;

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Admin() {
    }
}
