package com.residents.dubaiassetmanagement.Profile;

public class HouseHelp {

    private String name;
    private String lastName;
    private String contact;
    private int position;
    private String occupanId;



    public HouseHelp(String name, String lastName, String contact, int position,String occupanId) {
        this.name = name;
        this.lastName = lastName;
        this.contact = contact;
        this.position = position;
        this.occupanId = occupanId;
    }
    public String getOccupanId() {
        return occupanId;
    }

    public void setOccupanId(String occupanId) {
        this.occupanId = occupanId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
