package com.fyp.sporteaze.Model;

public class UserTeamInfo {
    String team_id, status, team_name, created_by;


    public UserTeamInfo(String team_id, String status, String team_name, String created_by) {
        this.team_id = team_id;
        this.status = status;
        this.team_name = team_name;
        this.created_by = created_by;
    }

    public UserTeamInfo(){
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }


}
