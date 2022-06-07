package com.fyp.sporteaze.Model;

public class JoinTeam {
    String request_id, user_id, team_name, user_name, user_email, user_phone, user_dob, user_address;

    public JoinTeam() {
    }

    public JoinTeam(String request_id, String user_id, String team_name, String user_name, String user_email, String user_phone, String user_dob, String user_address) {
        this.request_id = request_id;
        this.user_id = user_id;
        this.team_name = team_name;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_dob = user_dob;
        this.user_address = user_address;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }



    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_dob() {
        return user_dob;
    }

    public void setUser_dob(String user_dob) {
        this.user_dob = user_dob;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }
}
