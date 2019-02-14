
package com.residents.dubaiassetmanagement.Model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TenantDetails implements Parcelable
{

    @SerializedName("Tenant_Details")
    @Expose
    private List<TenantDetail> tenantDetails = null;
    @SerializedName("Occupant_Details")
    @Expose
    private List<OccupantDetail> occupantDetails = null;
    @SerializedName("Pet_Details")
    @Expose
    private List<PetDetail> petDetails = null;
    public final static Creator<TenantDetails> CREATOR = new Creator<TenantDetails>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TenantDetails createFromParcel(Parcel in) {
            return new TenantDetails(in);
        }

        public TenantDetails[] newArray(int size) {
            return (new TenantDetails[size]);
        }

    }
    ;

    protected TenantDetails(Parcel in) {
        in.readList(this.tenantDetails, (TenantDetail.class.getClassLoader()));
        in.readList(this.occupantDetails, (OccupantDetail.class.getClassLoader()));
        in.readList(this.petDetails, (PetDetail.class.getClassLoader()));
    }

    public TenantDetails() {
    }

    public List<TenantDetail> getTenantDetails() {
        return tenantDetails;
    }

    public void setTenantDetails(List<TenantDetail> tenantDetails) {
        this.tenantDetails = tenantDetails;
    }

    public List<OccupantDetail> getOccupantDetails() {
        return occupantDetails;
    }

    public void setOccupantDetails(List<OccupantDetail> occupantDetails) {
        this.occupantDetails = occupantDetails;
    }

    public List<PetDetail> getPetDetails() {
        return petDetails;
    }

    public void setPetDetails(List<PetDetail> petDetails) {
        this.petDetails = petDetails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(tenantDetails);
        dest.writeList(occupantDetails);
        dest.writeList(petDetails);
    }

    public int describeContents() {
        return  0;
    }

}
