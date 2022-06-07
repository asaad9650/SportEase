package com.fyp.sporteaze.Model;

public class Invitation {
    String captain_email, team_name,  remaining_space, looking_for, preferred_age , message, invitation_id;

    public Invitation(String captain_email, String team_name, String remaining_space, String looking_for, String preferred_age, String message, String invitation_id) {
        this.captain_email = captain_email;
        this.team_name = team_name;
        this.remaining_space = remaining_space;
        this.looking_for = looking_for;
        this.preferred_age = preferred_age;
        this.message = message;
        this.invitation_id = invitation_id;
    }

    public Invitation() {
    }

    public String getCaptain_email() {
        return captain_email;
    }

    public void setCaptain_email(String captain_email) {
        this.captain_email = captain_email;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getRemaining_space() {
        return remaining_space;
    }

    public void setRemaining_space(String remaining_space) {
        this.remaining_space = remaining_space;
    }

    public String getLooking_for() {
        return looking_for;
    }

    public void setLooking_for(String looking_for) {
        this.looking_for = looking_for;
    }

    public String getPreferred_age() {
        return preferred_age;
    }

    public void setPreferred_age(String preferred_age) {
        this.preferred_age = preferred_age;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInvitation_id() {
        return invitation_id;
    }

    public void setInvitation_id(String invitation_id) {
        this.invitation_id = invitation_id;
    }

}
