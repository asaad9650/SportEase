package com.fyp.sporteaze.Model;

public class Match {
    String match_id, team_name, ground_booked, winning_price, team_id , email_1_captain , email_2_vice_captain, email_3, email_4, email_5, email_6, email_7, email_8, email_9, email_10, email_11 , total_matches_played, matches_won, matches_lost;

    public Match(String match_id, String team_name, String ground_booked, String winning_price, String team_id, String email_1_captain, String email_2_vice_captain, String email_3, String email_4, String email_5, String email_6, String email_7, String email_8, String email_9, String email_10, String email_11, String total_matches_played, String matches_won, String matches_lost) {
        this.match_id = match_id;
        this.team_name = team_name;
        this.ground_booked = ground_booked;
        this.winning_price = winning_price;
        this.team_id = team_id;
        this.email_1_captain = email_1_captain;
        this.email_2_vice_captain = email_2_vice_captain;
        this.email_3 = email_3;
        this.email_4 = email_4;
        this.email_5 = email_5;
        this.email_6 = email_6;
        this.email_7 = email_7;
        this.email_8 = email_8;
        this.email_9 = email_9;
        this.email_10 = email_10;
        this.email_11 = email_11;
        this.total_matches_played = total_matches_played;
        this.matches_won = matches_won;
        this.matches_lost = matches_lost;
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

    public String getEmail_1_captain() {
        return email_1_captain;
    }

    public void setEmail_1_captain(String email_1_captain) {
        this.email_1_captain = email_1_captain;
    }

    public String getEmail_2_vice_captain() {
        return email_2_vice_captain;
    }

    public void setEmail_2_vice_captain(String email_2_vice_captain) {
        this.email_2_vice_captain = email_2_vice_captain;
    }

    public String getEmail_3() {
        return email_3;
    }

    public void setEmail_3(String email_3) {
        this.email_3 = email_3;
    }

    public String getEmail_4() {
        return email_4;
    }

    public void setEmail_4(String email_4) {
        this.email_4 = email_4;
    }

    public String getEmail_5() {
        return email_5;
    }

    public void setEmail_5(String email_5) {
        this.email_5 = email_5;
    }

    public String getEmail_6() {
        return email_6;
    }

    public void setEmail_6(String email_6) {
        this.email_6 = email_6;
    }

    public String getEmail_7() {
        return email_7;
    }

    public void setEmail_7(String email_7) {
        this.email_7 = email_7;
    }

    public String getEmail_8() {
        return email_8;
    }

    public void setEmail_8(String email_8) {
        this.email_8 = email_8;
    }

    public String getEmail_9() {
        return email_9;
    }

    public void setEmail_9(String email_9) {
        this.email_9 = email_9;
    }

    public String getEmail_10() {
        return email_10;
    }

    public void setEmail_10(String email_10) {
        this.email_10 = email_10;
    }

    public String getEmail_11() {
        return email_11;
    }

    public void setEmail_11(String email_11) {
        this.email_11 = email_11;
    }

    public String getTotal_matches_played() {
        return total_matches_played;
    }

    public void setTotal_matches_played(String total_matches_played) {
        this.total_matches_played = total_matches_played;
    }

    public String getMatches_won() {
        return matches_won;
    }

    public void setMatches_won(String matches_won) {
        this.matches_won = matches_won;
    }

    public String getMatches_lost() {
        return matches_lost;
    }

    public void setMatches_lost(String matches_lost) {
        this.matches_lost = matches_lost;
    }
}