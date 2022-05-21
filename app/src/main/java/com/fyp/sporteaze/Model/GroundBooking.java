package com.fyp.sporteaze.Model;

public class GroundBooking {

    public String academy_id,ground_request_status, booked_by_id,ground_booking_id,ground_id, ground_name, ground_location, ground_size,ground_timings,ground_booking_purpose,with_medical_trainer, with_analyst, ground_charges;

    public GroundBooking(String academy_id, String ground_request_status, String booked_by_id, String ground_booking_id, String ground_id, String ground_name, String ground_location, String ground_size, String ground_timings, String ground_booking_purpose, String with_medical_trainer, String with_analyst, String ground_charges) {
        this.academy_id = academy_id;
        this.ground_request_status = ground_request_status;
        this.booked_by_id = booked_by_id;
        this.ground_booking_id = ground_booking_id;
        this.ground_id = ground_id;
        this.ground_name = ground_name;
        this.ground_location = ground_location;
        this.ground_size = ground_size;
        this.ground_timings = ground_timings;
        this.ground_booking_purpose = ground_booking_purpose;
        this.with_medical_trainer = with_medical_trainer;
        this.with_analyst = with_analyst;
        this.ground_charges = ground_charges;
    }

    public GroundBooking(){

    }

    public String getAcademy_id() {
        return academy_id;
    }

    public void setAcademy_id(String academy_id) {
        this.academy_id = academy_id;
    }

    public String getGround_request_status() {
        return ground_request_status;
    }

    public void setGround_request_status(String ground_request_status) {
        this.ground_request_status = ground_request_status;
    }

    public String getGround_charges() {
        return ground_charges;
    }

    public void setGround_charges(String ground_charges) {
        this.ground_charges = ground_charges;
    }

    public String getBooked_by_id() {
        return booked_by_id;
    }

    public void setBooked_by_id(String booked_by_id) {
        this.booked_by_id = booked_by_id;
    }

    public String getGround_booking_id() {
        return ground_booking_id;
    }

    public void setGround_booking_id(String ground_booking_id) {
        this.ground_booking_id = ground_booking_id;
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

    public String getGround_location() {
        return ground_location;
    }

    public void setGround_location(String ground_location) {
        this.ground_location = ground_location;
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

    public String getGround_booking_purpose() {
        return ground_booking_purpose;
    }

    public void setGround_booking_purpose(String ground_booking_purpose) {
        this.ground_booking_purpose = ground_booking_purpose;
    }

    public String getWith_medical_trainer() {
        return with_medical_trainer;
    }

    public void setWith_medical_trainer(String with_medical_trainer) {
        this.with_medical_trainer = with_medical_trainer;
    }

    public String getWith_analyst() {
        return with_analyst;
    }

    public void setWith_analyst(String with_analyst) {
        this.with_analyst = with_analyst;
    }
}
