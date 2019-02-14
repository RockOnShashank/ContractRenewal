
package com.residents.dubaiassetmanagement.contract_renewal.confirm_details.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaseCharges implements Parcelable
{

    @SerializedName("ME_Lease_charges_V")
    @Expose
    private List<MELeaseChargesV> mELeaseChargesV = null;
    public final static Creator<LeaseCharges> CREATOR = new Creator<LeaseCharges>() {


        @SuppressWarnings({
            "unchecked"
        })
        public LeaseCharges createFromParcel(Parcel in) {
            return new LeaseCharges(in);
        }

        public LeaseCharges[] newArray(int size) {
            return (new LeaseCharges[size]);
        }

    }
    ;

    protected LeaseCharges(Parcel in) {
        in.readList(this.mELeaseChargesV, (MELeaseChargesV.class.getClassLoader()));
    }

    public LeaseCharges() {
    }

    public List<MELeaseChargesV> getMELeaseChargesV() {
        return mELeaseChargesV;
    }

    public void setMELeaseChargesV(List<MELeaseChargesV> mELeaseChargesV) {
        this.mELeaseChargesV = mELeaseChargesV;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mELeaseChargesV);
    }

    public int describeContents() {
        return  0;
    }

}
