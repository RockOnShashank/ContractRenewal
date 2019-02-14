
package com.residents.dubaiassetmanagement.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OccupantDetail implements Parcelable
{

    @SerializedName("Tenant_Code")
    @Expose
    private String tenantCode;
    @SerializedName("Occupant_Id")
    @Expose
    private String occupantId;
    @SerializedName("Occupant_Name")
    @Expose
    private String occupantName;
    @SerializedName("Occupant_Relation")
    @Expose
    private String occupantRelation;
    @SerializedName("Occupant_Email")
    @Expose
    private String occupantEmail;
    @SerializedName("Occupant_Mobile")
    @Expose
    private String occupantMobile;

    @SerializedName("position")
    @Expose
    private int position;

    public final static Creator<OccupantDetail> CREATOR = new Creator<OccupantDetail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OccupantDetail createFromParcel(Parcel in) {
            return new OccupantDetail(in);
        }

        public OccupantDetail[] newArray(int size) {
            return (new OccupantDetail[size]);
        }

    }
    ;
    public OccupantDetail(String tenantCode, String occupantId, String occupantName, String occupantRelation, String occupantEmail, String occupantMobile, int position) {
        this.tenantCode = tenantCode;
        this.occupantId = occupantId;
        this.occupantName = occupantName;
        this.occupantRelation = occupantRelation;
        this.occupantEmail = occupantEmail;
        this.occupantMobile = occupantMobile;
        this.position = position;
    }


    public OccupantDetail(String name, String contact, int  position, String occupantId) {
        this.occupantId = occupantId;
        this.occupantName = name;
        this.occupantMobile = contact;
        this.position = position;
    }
    protected OccupantDetail(Parcel in) {
        this.tenantCode = ((String) in.readValue((String.class.getClassLoader())));
        this.occupantId = ((String) in.readValue((String.class.getClassLoader())));
        this.occupantName = ((String) in.readValue((String.class.getClassLoader())));
        this.occupantRelation = ((String) in.readValue((String.class.getClassLoader())));
        this.occupantEmail = ((String) in.readValue((String.class.getClassLoader())));
        this.occupantMobile = ((String) in.readValue((String.class.getClassLoader())));
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getOccupantId() {
        return occupantId;
    }

    public void setOccupantId(String occupantId) {
        this.occupantId = occupantId;
    }

    public String getOccupantName() {
        return occupantName;
    }

    public void setOccupantName(String occupantName) {
        this.occupantName = occupantName;
    }

    public String getOccupantRelation() {
        return occupantRelation;
    }

    public void setOccupantRelation(String occupantRelation) {
        this.occupantRelation = occupantRelation;
    }

    public String getOccupantEmail() {
        return occupantEmail;
    }

    public void setOccupantEmail(String occupantEmail) {
        this.occupantEmail = occupantEmail;
    }

    public String getOccupantMobile() {
        return occupantMobile;
    }

    public void setOccupantMobile(String occupantMobile) {
        this.occupantMobile = occupantMobile;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(tenantCode);
        dest.writeValue(occupantId);
        dest.writeValue(occupantName);
        dest.writeValue(occupantRelation);
        dest.writeValue(occupantEmail);
        dest.writeValue(occupantMobile);
    }

    public int describeContents() {
        return  0;
    }

}
