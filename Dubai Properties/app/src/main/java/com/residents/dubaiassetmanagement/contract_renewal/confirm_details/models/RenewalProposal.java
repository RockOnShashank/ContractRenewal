
package com.residents.dubaiassetmanagement.contract_renewal.confirm_details.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RenewalProposal implements Parcelable
{

    @SerializedName("Res_Renewal_Proposal")
    @Expose
    private ResRenewalProposal resRenewalProposal;
    @SerializedName("RES_Renewal_Specials")
    @Expose
    private RESRenewalSpecials rESRenewalSpecials;
    @SerializedName("Lease_charges")
    @Expose
    private LeaseCharges leaseCharges;
    @SerializedName("TotalPayableAmount")
    @Expose
    private String totalPayableAmount;
    @SerializedName("Status")
    @Expose
    private Object status;
    @SerializedName("Error_Message")
    @Expose
    private Object errorMessage;
    public final static Creator<RenewalProposal> CREATOR = new Creator<RenewalProposal>() {


        @SuppressWarnings({
            "unchecked"
        })
        public RenewalProposal createFromParcel(Parcel in) {
            return new RenewalProposal(in);
        }

        public RenewalProposal[] newArray(int size) {
            return (new RenewalProposal[size]);
        }

    }
    ;

    protected RenewalProposal(Parcel in) {
        this.resRenewalProposal = ((ResRenewalProposal) in.readValue((ResRenewalProposal.class.getClassLoader())));
        this.rESRenewalSpecials = ((RESRenewalSpecials) in.readValue((RESRenewalSpecials.class.getClassLoader())));
        this.leaseCharges = ((LeaseCharges) in.readValue((LeaseCharges.class.getClassLoader())));
        this.totalPayableAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Object) in.readValue((Object.class.getClassLoader())));
        this.errorMessage = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public RenewalProposal() {
    }

    public ResRenewalProposal getResRenewalProposal() {
        return resRenewalProposal;
    }

    public void setResRenewalProposal(ResRenewalProposal resRenewalProposal) {
        this.resRenewalProposal = resRenewalProposal;
    }

    public RESRenewalSpecials getRESRenewalSpecials() {
        return rESRenewalSpecials;
    }

    public void setRESRenewalSpecials(RESRenewalSpecials rESRenewalSpecials) {
        this.rESRenewalSpecials = rESRenewalSpecials;
    }

    public LeaseCharges getLeaseCharges() {
        return leaseCharges;
    }

    public void setLeaseCharges(LeaseCharges leaseCharges) {
        this.leaseCharges = leaseCharges;
    }

    public String getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(String totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(resRenewalProposal);
        dest.writeValue(rESRenewalSpecials);
        dest.writeValue(leaseCharges);
        dest.writeValue(totalPayableAmount);
        dest.writeValue(status);
        dest.writeValue(errorMessage);
    }

    public int describeContents() {
        return  0;
    }

}
