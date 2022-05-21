package com.fyp.sporteaze.Model;

public class Academy_registration_individual {

    String id,user_id, user_name,user_phone,user_email,user_address,academy_id,academy_name, request_status;


    public Academy_registration_individual(String id, String user_id, String user_name,  String user_phone, String user_email, String user_address, String academy_id, String academy_name, String request_status) {
        this.id = id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.user_address = user_address;
        this.academy_id = academy_id;
        this.academy_name = academy_name;
        this.request_status = request_status;
    }

    public Academy_registration_individual(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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


    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getAcademy_id() {
        return academy_id;
    }

    public void setAcademy_id(String academy_id) {
        this.academy_id = academy_id;
    }

    public String getAcademy_name() {
        return academy_name;
    }

    public void setAcademy_name(String academy_name) {
        this.academy_name = academy_name;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }
}
