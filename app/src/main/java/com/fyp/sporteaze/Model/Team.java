package com.fyp.sporteaze.Model;

public class Team {
    public String team_name;
        public String email_1_captain,email_2_vice_captain,email_3,email_4,email_5,email_6,email_7,email_8,email_9,email_10,email_11;
    public String team_id;
    public String created_by;


    public Team(String team_name, String email_1_captain, String email_2_vice_captain, String email_3, String email_4, String email_5, String email_6, String email_7, String email_8, String email_9, String email_10, String email_11, String team_id, String created_by) {
        this.team_name = team_name;
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
        this.team_id = team_id;
        this.created_by = created_by;
    }

    public Team(){}

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
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

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }
}
