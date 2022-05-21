package com.fyp.sporteaze.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String name,email,password,cnic,phone,dob,address,gender,user_id , image, enrolled, captain;

    public User(String name, String email, String password, String cnic, String phone, String dob, String address, String gender, String user_id, String image, String enrolled, String captain) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cnic = cnic;
        this.phone = phone;
        this.dob = dob;
        this.address = address;
        this.gender = gender;
        this.user_id = user_id;
        this.image = image;
        this.enrolled = enrolled;
        this.captain = captain;
    }

    public User() {

    }
}

