package com.fyp.sporteaze.Model;

public class Coach {

    String academy_id,coach_image,coach_name, coach_address,coach_age,coach_expertise,coach_phone,coach_charges, coach_id;

    public Coach(String academy_id, String coach_image, String coach_name, String coach_address, String coach_age, String coach_expertise, String coach_phone, String coach_charges, String coach_id) {
        this.academy_id = academy_id;
        this.coach_image = coach_image;
        this.coach_name = coach_name;
        this.coach_address = coach_address;
        this.coach_age = coach_age;
        this.coach_expertise = coach_expertise;
        this.coach_phone = coach_phone;
        this.coach_charges = coach_charges;
        this.coach_id = coach_id;
    }

    public Coach(){

    }


    public String getAcademy_id() {
        return academy_id;
    }

    public void setAcademy_id(String academy_id) {
        this.academy_id = academy_id;
    }

    public String getCoach_image() {
        return coach_image;
    }

    public void setCoach_image(String coach_image) {
        this.coach_image = coach_image;
    }

    public String getCoach_name() {
        return coach_name;
    }

    public void setCoach_name(String coach_name) {
        this.coach_name = coach_name;
    }

    public String getCoach_address() {
        return coach_address;
    }

    public void setCoach_address(String coach_address) {
        this.coach_address = coach_address;
    }

    public String getCoach_age() {
        return coach_age;
    }

    public void setCoach_age(String coach_age) {
        this.coach_age = coach_age;
    }

    public String getCoach_expertise() {
        return coach_expertise;
    }

    public void setCoach_expertise(String coach_expertise) {
        this.coach_expertise = coach_expertise;
    }

    public String getCoach_phone() {
        return coach_phone;
    }

    public void setCoach_phone(String coach_phone) {
        this.coach_phone = coach_phone;
    }

    public String getCoach_charges() {
        return coach_charges;
    }

    public void setCoach_charges(String coach_charges) {
        this.coach_charges = coach_charges;
    }

    public String getCoach_id() {
        return coach_id;
    }

    public void setCoach_id(String coach_id) {
        this.coach_id = coach_id;
    }
}
