package com.example.a310finalproj;


import java.util.Date;

public class Invitation {

    // Data members as per design doc
    // private access
    private String invitationId;
    private String userId;
    private String biography;
    private String address;
    private double rent;
    private double utilities;
    private int bedrooms;
    private int beds;
    private int bathrooms;
    private boolean pets;
    private Date deadline;

    public Invitation(){}

    public Invitation(String invId, String uId, String addr, String bio, double _rent, double _utilities, int _bedrooms, int _beds, int _bathrooms, boolean _pets, Date _deadline){
        invitationId = invId;
        userId = uId;
        address = addr;
        rent = _rent;
        utilities = _utilities;
        bedrooms = _bedrooms;
        beds=_beds;
        bathrooms = _bathrooms;
        pets = _pets;
        deadline = _deadline;
    }

    public Invitation(){};

    // Member functions as per design doc

    public String getInvitationId() {
        return invitationId;
    }

    public String getUserId() {
        return userId;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public double getUtilities() {
        return utilities;
    }

    public void setUtilities(double utilities) {
        this.utilities = utilities;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    public boolean isPets() {
        return pets;
    }

    public void setPets(boolean pets) {
        this.pets = pets;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}

