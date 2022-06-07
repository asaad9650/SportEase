package com.fyp.sporteaze.Model;

public class Invoice {
    String date_time;
    String event;
    String basic_charges;
    String medical_trainer_charges;
    String analyst_charges;
    String total_charges;
    String invoice_id;

    public Invoice(String date_time, String event, String basic_charges, String medical_trainer_charges, String analyst_charges, String total_charges, String invoice_id )  {
        this.date_time = date_time;
        this.event = event;
        this.basic_charges = basic_charges;
        this.medical_trainer_charges = medical_trainer_charges;
        this.analyst_charges = analyst_charges;
        this.total_charges = total_charges;
        this.invoice_id = invoice_id;
    }

    public Invoice(){}


    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getBasic_charges() {
        return basic_charges;
    }

    public void setBasic_charges(String basic_charges) {
        this.basic_charges = basic_charges;
    }

    public String getMedical_trainer_charges() {
        return medical_trainer_charges;
    }

    public void setMedical_trainer_charges(String medical_trainer_charges) {
        this.medical_trainer_charges = medical_trainer_charges;
    }

    public String getAnalyst_charges() {
        return analyst_charges;
    }

    public void setAnalyst_charges(String analyst_charges) {
        this.analyst_charges = analyst_charges;
    }

    public String getTotal_charges() {
        return total_charges;
    }

    public void setTotal_charges(String total_charges) {
        this.total_charges = total_charges;
    }
}
