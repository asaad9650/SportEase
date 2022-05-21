package com.fyp.sporteaze.Model;

public class Ground {
    String academy_id,ground_id,ground_name, ground_address, ground_size,ground_timings,ground_medical_trainer_availability,ground_analyst_availability, ground_image , ground_charges;

    public Ground(String academy_id, String ground_id, String ground_name, String ground_address, String ground_size, String ground_timings, String ground_medical_trainer_availability, String ground_analyst_availability, String ground_image, String ground_charges) {
        this.academy_id = academy_id;
        this.ground_id = ground_id;
        this.ground_name = ground_name;
        this.ground_address = ground_address;
        this.ground_size = ground_size;
        this.ground_timings = ground_timings;
        this.ground_medical_trainer_availability = ground_medical_trainer_availability;
        this.ground_analyst_availability = ground_analyst_availability;
        this.ground_image = ground_image;
        this.ground_charges = ground_charges;
    }

    public Ground(){}


    public String getAcademy_id() {
        return academy_id;
    }

    public void setAcademy_id(String academy_id) {
        this.academy_id = academy_id;
    }

    public String getGround_charges() {
        return ground_charges;
    }

    public void setGround_charges(String ground_charges) {
        this.ground_charges = ground_charges;
    }

    public String getGround_id() {
        return ground_id;
    }

    public void setGround_id(String ground_id) {
        this.ground_id = ground_id;
    }

    public String getGround_name() {
        return ground_name;
    }

    public void setGround_name(String ground_name) {
        this.ground_name = ground_name;
    }

    public String getGround_address() {
        return ground_address;
    }

    public void setGround_address(String ground_address) {
        this.ground_address = ground_address;
    }

    public String getGround_size() {
        return ground_size;
    }

    public void setGround_size(String ground_size) {
        this.ground_size = ground_size;
    }

    public String getGround_timings() {
        return ground_timings;
    }

    public void setGround_timings(String ground_timings) {
        this.ground_timings = ground_timings;
    }

    public String getGround_medical_trainer_availability() {
        return ground_medical_trainer_availability;
    }

    public void setGround_medical_trainer_availability(String ground_medical_trainer_availability) {
        this.ground_medical_trainer_availability = ground_medical_trainer_availability;
    }

    public String getGround_analyst_availability() {
        return ground_analyst_availability;
    }

    public void setGround_analyst_availability(String ground_analyst_availability) {
        this.ground_analyst_availability = ground_analyst_availability;
    }

    public String getGround_image() {
        return ground_image;
    }

    public void setGround_image(String ground_image) {
        this.ground_image = ground_image;
    }
}
