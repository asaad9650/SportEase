package com.fyp.sporteaze.Model;

public class CreateMatchBetweenTeams {
    String created_team_match_id, request_initiated_by;
    Match team_1 , team_2;
    String match_date;
    String match_venue;
    String status;

    public CreateMatchBetweenTeams() {
    }

    public CreateMatchBetweenTeams(String created_team_match_id, String request_initiated_by, Match team_1, Match team_2, String match_date, String match_venue , String status) {
        this.created_team_match_id = created_team_match_id;
        this.request_initiated_by = request_initiated_by;
        this.team_1 = team_1;
        this.team_2 = team_2;
        this.match_date = match_date;
        this.match_venue = match_venue;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_team_match_id() {
        return created_team_match_id;
    }

    public void setCreated_team_match_id(String created_team_match_id) {
        this.created_team_match_id = created_team_match_id;
    }

    public String getRequest_initiated_by() {
        return request_initiated_by;
    }

    public void setRequest_initiated_by(String request_initiated_by) {
        this.request_initiated_by = request_initiated_by;
    }

    public Match getTeam_1() {
        return team_1;
    }

    public void setTeam_1(Match team_1) {
        this.team_1 = team_1;
    }

    public Match getTeam_2() {
        return team_2;
    }

    public void setTeam_2(Match team_2) {
        this.team_2 = team_2;
    }

    public String getMatch_date() {
        return match_date;
    }

    public void setMatch_date(String match_date) {
        this.match_date = match_date;
    }

    public String getMatch_venue() {
        return match_venue;
    }

    public void setMatch_venue(String match_venue) {
        this.match_venue = match_venue;
    }
}
