package com.residents.dubaiassetmanagement.Profile;

public class PetDetails {

    private String petName;
    private String petType;
    private String petBreed;
    private int position;


    private String petId;
    public PetDetails(String petName, String petType, String petBreed, int position, String petId) {
        this.petName = petName;
        this.petType = petType;
        this.petBreed = petBreed;
        this.position = position;
        this.petId = petId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }
    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }


}
