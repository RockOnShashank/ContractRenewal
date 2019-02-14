
package com.residents.dubaiassetmanagement.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PetDetail implements Parcelable
{

    @SerializedName("Pet_Id")
    @Expose
    private String petId;
    @SerializedName("Pet_Weight")
    @Expose
    private String petWeight;
    @SerializedName("Tenant_Code")
    @Expose
    private String tenantCode;
    @SerializedName("Pet_Name")
    @Expose
    private String petName;
    @SerializedName("Pet_Type")
    @Expose
    private String petType;


    public static Creator<PetDetail> getCREATOR() {
        return CREATOR;
    }

    @SerializedName("petBreed")
    @Expose
    private String petBreed;

    @SerializedName("position")
    @Expose
    private int position;

    public final static Creator<PetDetail> CREATOR = new Creator<PetDetail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PetDetail createFromParcel(Parcel in) {
            return new PetDetail(in);
        }

        public PetDetail[] newArray(int size) {
            return (new PetDetail[size]);
        }

    }
    ;

    public PetDetail(String petName, String petType, int position, String petId) {
        this.petName = petName;
        this.petType = petType;
        this.position = position;
        this.petId = petId;
    }
    protected PetDetail(Parcel in) {
        this.petId = ((String) in.readValue((String.class.getClassLoader())));
        this.petWeight = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantCode = ((String) in.readValue((String.class.getClassLoader())));
        this.petName = ((String) in.readValue((String.class.getClassLoader())));
        this.petType = ((String) in.readValue((String.class.getClassLoader())));
    }


    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(String petWeight) {
        this.petWeight = petWeight;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(petId);
        dest.writeValue(petWeight);
        dest.writeValue(tenantCode);
        dest.writeValue(petName);
        dest.writeValue(petType);
    }

    public int describeContents() {
        return  0;
    }

}
