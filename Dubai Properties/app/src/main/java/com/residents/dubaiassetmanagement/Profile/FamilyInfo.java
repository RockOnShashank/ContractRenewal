package com.residents.dubaiassetmanagement.Profile;

public class FamilyInfo {

    private String firstName;
    private String lastName;
    private String relationship;
    private String email;
    private String mobileNumber;
    private int pos;
    private String occupantId;

    public String getOccupantId() {
        return occupantId;
    }

    public void setOccupantId(String occupantId) {
        this.occupantId = occupantId;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }


   public FamilyInfo(String firstName, String lastName, String relationship, String email, String mobileNumber, int position, String occupantId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.relationship = relationship;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.pos = position;
        this.occupantId = occupantId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


}
