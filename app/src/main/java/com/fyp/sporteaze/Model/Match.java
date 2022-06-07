package com.fyp.sporteaze.Model;

public class Match {
    String match_id, team_name, ground_booked, winning_price, team_id;

    public Match(String match_id, String team_name, String ground_booked, String winning_price, String team_id) {
        this.match_id = match_id;
        this.team_name = team_name;
        this.ground_booked = ground_booked;
        this.winning_price = winning_price;
        this.team_id = team_id;
    }

    public Match() {
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getGround_booked() {
        return ground_booked;
    }

    public void setGround_booked(String ground_booked) {
        this.ground_booked = ground_booked;
    }

    public String getWinning_price() {
        return winning_price;
    }

    public void setWinning_price(String winning_price) {
        this.winning_price = winning_price;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }
}