
package com.residents.dubaiassetmanagement.contract_renewal.confirm_details.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RESRenewalSpecials implements Parcelable
{

    @SerializedName("ME_RES_Renewal_Specials_V")
    @Expose
    private MERESRenewalSpecialsV mERESRenewalSpecialsV;
    public final static Creator<RESRenewalSpecials> CREATOR = new Creator<RESRenewalSpecials>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RESRenewalSpecials createFromParcel(Parcel in) {
            return new RESRenewalSpecials(in);
        }

        public RESRenewalSpecials[] newArray(int size) {
            return (new RESRenewalSpecials[size]);
        }

    }
    ;

    protected RESRenewalSpecials(Parcel in) {
        this.mERESRenewalSpecialsV = ((MERESRenewalSpecialsV) in.readValue((MERESRenewalSpecialsV.class.getClassLoader())));
    }

    public RESRenewalSpecials() {
    }

    public MERESRenewalSpecialsV getMERESRenewalSpecialsV() {
        return mERESRenewalSpecialsV;
    }

    public void setMERESRenewalSpecialsV(MERESRenewalSpecialsV mERESRenewalSpecialsV) {
        this.mERESRenewalSpecialsV = mERESRenewalSpecialsV;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mERESRenewalSpecialsV);
    }

    public int describeContents() {
        return  0;
    }

}
