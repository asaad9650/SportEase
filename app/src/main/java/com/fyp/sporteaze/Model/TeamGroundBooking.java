package com.fyp.sporteaze.Model;

public class TeamGroundBooking {
    String booking_id, team_id, ground_name, ground_id, ground_size, ground_location, ground_timings, ground_date, ground_booking_purpose , with_medical , with_analyst , ground_charges, request_status , academy_id , team_name;

    public TeamGroundBooking(String booking_id, String team_id, String ground_name, String ground_id, String ground_size, String ground_location, String ground_timings, String ground_date, String ground_booking_purpose, String with_medical, String with_analyst, String ground_charges, String request_status, String academy_id, String team_name) {
        this.booking_id = booking_id;
        this.team_id = team_id;
        this.ground_name = ground_name;
        this.ground_id = ground_id;
        this.ground_size = ground_size;
        this.ground_location = ground_location;
        this.ground_timings = ground_timings;
        this.ground_date = ground_date;
        this.ground_booking_purpose = ground_booking_purpose;
        this.with_medical = with_medical;
        this.with_analyst = with_analyst;
        this.ground_charges = ground_charges;
        this.request_status = request_status;
        this.academy_id = academy_id;
        this.team_name = team_name;
    }

    public TeamGroundBooking() {
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

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

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getGround_name() {
        return ground_name;
    }

    public void setGround_name(String ground_name) {
        this.ground_name = ground_name;
    }

    public String getGround_id() {
        return ground_id;
    }

    public void setGround_id(String ground_id) {
        this.ground_id = ground_id;
    }

    public String getGround_size() {
        return ground_size;
    }

    public void setGround_size(String ground_size) {
        this.ground_size = ground_size;
    }

    public String getGround_location() {
        return ground_location;
    }

    public void setGround_location(String ground_location) {
        this.ground_location = ground_location;
    }

    public String getGround_timings() {
        return ground_timings;
    }

    public void setGround_timings(String ground_timings) {
        this.ground_timings = ground_timings;
    }

    public String getGround_date() {
        return ground_date;
    }

    public void setGround_date(String ground_date) {
        this.ground_date = ground_date;
    }

    public String getGround_booking_purpose() {
        return ground_booking_purpose;
    }

    public void setGround_booking_purpose(String ground_booking_purpose) {
        this.ground_booking_purpose = ground_booking_purpose;
    }

    public String getWith_medical() {
        return with_medical;
    }

    public void setWith_medical(String with_medical) {
        this.with_medical = with_medical;
    }

    public String getWith_analyst() {
        return with_analyst;
    }

    public void setWith_analyst(String with_analyst) {
        this.with_analyst = with_analyst;
    }
}
