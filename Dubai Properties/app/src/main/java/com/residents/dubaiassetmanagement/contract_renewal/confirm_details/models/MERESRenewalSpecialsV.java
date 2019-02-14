
package com.residents.dubaiassetmanagement.contract_renewal.confirm_details.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MERESRenewalSpecialsV implements Parcelable
{

    @SerializedName("SPECIAL_TYPE")
    @Expose
    private String sPECIALTYPE;
    @SerializedName("CHARGE_CODE")
    @Expose
    private String cHARGECODE;
    @SerializedName("CHARGE_AMOUNT")
    @Expose
    private String cHARGEAMOUNT;
    @SerializedName("PERCENTAGE")
    @Expose
    private String pERCENTAGE;
    public final static Creator<MERESRenewalSpecialsV> CREATOR = new Creator<MERESRenewalSpecialsV>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MERESRenewalSpecialsV createFromParcel(Parcel in) {
            return new MERESRenewalSpecialsV(in);
        }

        public MERESRenewalSpecialsV[] newArray(int size) {
            return (new MERESRenewalSpecialsV[size]);
        }

    }
    ;

    protected MERESRenewalSpecialsV(Parcel in) {
        this.sPECIALTYPE = ((String) in.readValue((String.class.getClassLoader())));
        this.cHARGECODE = ((String) in.readValue((String.class.getClassLoader())));
        this.cHARGEAMOUNT = ((String) in.readValue((String.class.getClassLoader())));
        this.pERCENTAGE = ((String) in.readValue((String.class.getClassLoader())));
    }

    public MERESRenewalSpecialsV() {
    }

    public String getSPECIALTYPE() {
        return sPECIALTYPE;
    }

    public void setSPECIALTYPE(String sPECIALTYPE) {
        this.sPECIALTYPE = sPECIALTYPE;
    }

    public String getCHARGECODE() {
        return cHARGECODE;
    }

    public void setCHARGECODE(String cHARGECODE) {
        this.cHARGECODE = cHARGECODE;
    }

    public String getCHARGEAMOUNT() {
        return cHARGEAMOUNT;
    }

    public void setCHARGEAMOUNT(String cHARGEAMOUNT) {
        this.cHARGEAMOUNT = cHARGEAMOUNT;
    }

    public String getPERCENTAGE() {
        return pERCENTAGE;
    }

    public void setPERCENTAGE(String pERCENTAGE) {
        this.pERCENTAGE = pERCENTAGE;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sPECIALTYPE);
        dest.writeValue(cHARGECODE);
        dest.writeValue(cHARGEAMOUNT);
        dest.writeValue(pERCENTAGE);
    }

    public int describeContents() {
        return  0;
    }

}
