package com.fyp.sporteaze.Model;

public class Academy_registration_team {

    String id, team_name, team_id, captain_name,academy_id,academy_name,request_status;


    public Academy_registration_team(String id, String team_name, String team_id, String captain_name, String academy_id, String academy_name, String request_status) {
        this.id = id;
        this.team_name = team_name;
        this.team_id = team_id;
        this.captain_name = captain_name;
        this.academy_id = academy_id;
        this.academy_name = academy_name;
        this.request_status = request_status;
    }

    public Academy_registration_team(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getCaptain_name() {
        return captain_name;
    }

    public void setCaptain_name(String captain_name) {
        this.captain_name = captain_name;
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
