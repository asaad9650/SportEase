package com.fyp.sporteaze.Admin;

public class DataHolder {
    String academy_name, academy_phone, academy_address, academy_email,academy_password, academy_image , academy_id;


    public DataHolder(String academy_name, String academy_email, String academy_password, String academy_address, String academy_phone, String academy_image ,String academy_id) {
        this.academy_name = academy_name;
        this.academy_email = academy_email;
        this.academy_password = academy_password;
        this.academy_address = academy_address;
        this.academy_phone = academy_phone;
        this.academy_image = academy_image;
        this.academy_id = academy_id;
    }

    public DataHolder() {
    }

    public void setAcademy_name(String academy_name) {
        this.academy_name = academy_name;
    }

    public void setAcademy_phone(String academy_phone) {
        this.academy_phone = academy_phone;
    }

    public void setAcademy_address(String academy_address) {
        this.academy_address = academy_address;
    }

    public void setAcademy_email(String academy_email) {
        this.academy_email = academy_email;
    }

    public void setAcademy_password(String academy_password) {
        this.academy_password = academy_password;
    }

    public void setAcademy_image(String academy_image) {
        this.academy_image = academy_image;
    }

    public String getAcademy_name() {
        return academy_name;
    }

    public String getAcademy_phone() {
        return academy_phone;
    }

    public String getAcademy_address() {
        return academy_address;
    }

    public String getAcademy_email() {
        return academy_email;
    }

    public String getAcademy_password() {
        return academy_password;
    }

    public String getAcademy_image() {
        return academy_image;
    }

    public String getAcademy_id() {
        return academy_id;
    }

    public void setAcademy_id(String academy_id) {
        this.academy_id = academy_id;
    }
}
