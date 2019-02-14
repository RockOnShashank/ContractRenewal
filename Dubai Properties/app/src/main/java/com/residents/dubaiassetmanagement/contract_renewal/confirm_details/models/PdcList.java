
package com.residents.dubaiassetmanagement.contract_renewal.confirm_details.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PdcList implements Parcelable
{

    @SerializedName("InstallementDate")
    @Expose
    private String installementDate;
    @SerializedName("InstallmentAmount")
    @Expose
    private String installmentAmount;
    @SerializedName("RecieptName")
    @Expose
    private String recieptName;
    @SerializedName("AmountInWords")
    @Expose
    private String amountInWords;
    public final static Creator<PdcList> CREATOR = new Creator<PdcList>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PdcList createFromParcel(Parcel in) {
            return new PdcList(in);
        }

        public PdcList[] newArray(int size) {
            return (new PdcList[size]);
        }

    }
    ;

    protected PdcList(Parcel in) {
        this.installementDate = ((String) in.readValue((String.class.getClassLoader())));
        this.installmentAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.recieptName = ((String) in.readValue((String.class.getClassLoader())));
        this.amountInWords = ((String) in.readValue((String.class.getClassLoader())));
    }

    public PdcList() {
    }

    public String getInstallementDate() {
        return installementDate;
    }

    public void setInstallementDate(String installementDate) {
        this.installementDate = installementDate;
    }

    public String getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getRecieptName() {
        return recieptName;
    }

    public void setRecieptName(String recieptName) {
        this.recieptName = recieptName;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(installementDate);
        dest.writeValue(installmentAmount);
        dest.writeValue(recieptName);
        dest.writeValue(amountInWords);
    }

    public int describeContents() {
        return  0;
    }

}
