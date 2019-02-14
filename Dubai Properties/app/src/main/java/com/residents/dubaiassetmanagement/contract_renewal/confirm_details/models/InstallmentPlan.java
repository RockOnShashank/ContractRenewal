
package com.residents.dubaiassetmanagement.contract_renewal.confirm_details.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstallmentPlan implements Parcelable
{

    @SerializedName("NoOfInstallment")
    @Expose
    private Integer noOfInstallment;
    @SerializedName("RenewalProposal")
    @Expose
    private RenewalProposal renewalProposal;
    @SerializedName("pdcResponse")
    @Expose
    private PdcResponse pdcResponse;
    public final static Creator<InstallmentPlan> CREATOR = new Creator<InstallmentPlan>() {


        @SuppressWarnings({
            "unchecked"
        })
        public InstallmentPlan createFromParcel(Parcel in) {
            return new InstallmentPlan(in);
        }

        public InstallmentPlan[] newArray(int size) {
            return (new InstallmentPlan[size]);
        }

    }
    ;

    protected InstallmentPlan(Parcel in) {
        this.noOfInstallment = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.renewalProposal = ((RenewalProposal) in.readValue((RenewalProposal.class.getClassLoader())));
        this.pdcResponse = ((PdcResponse) in.readValue((PdcResponse.class.getClassLoader())));
    }

    public InstallmentPlan() {
    }

    public Integer getNoOfInstallment() {
        return noOfInstallment;
    }

    public void setNoOfInstallment(Integer noOfInstallment) {
        this.noOfInstallment = noOfInstallment;
    }

    public RenewalProposal getRenewalProposal() {
        return renewalProposal;
    }

    public void setRenewalProposal(RenewalProposal renewalProposal) {
        this.renewalProposal = renewalProposal;
    }

    public PdcResponse getPdcResponse() {
        return pdcResponse;
    }

    public void setPdcResponse(PdcResponse pdcResponse) {
        this.pdcResponse = pdcResponse;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(noOfInstallment);
        dest.writeValue(renewalProposal);
        dest.writeValue(pdcResponse);
    }

    public int describeContents() {
        return  0;
    }

}
