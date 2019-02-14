
package com.residents.dubaiassetmanagement.contract_renewal.confirm_details.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResRenewalProposal implements Parcelable
{

    @SerializedName("ME_Res_Renewal_Proposal_dtls_v")
    @Expose
    private MEResRenewalProposalDtlsV mEResRenewalProposalDtlsV;
    public final static Creator<ResRenewalProposal> CREATOR = new Creator<ResRenewalProposal>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ResRenewalProposal createFromParcel(Parcel in) {
            return new ResRenewalProposal(in);
        }

        public ResRenewalProposal[] newArray(int size) {
            return (new ResRenewalProposal[size]);
        }

    }
    ;

    protected ResRenewalProposal(Parcel in) {
        this.mEResRenewalProposalDtlsV = ((MEResRenewalProposalDtlsV) in.readValue((MEResRenewalProposalDtlsV.class.getClassLoader())));
    }

    public ResRenewalProposal() {
    }

    public MEResRenewalProposalDtlsV getMEResRenewalProposalDtlsV() {
        return mEResRenewalProposalDtlsV;
    }

    public void setMEResRenewalProposalDtlsV(MEResRenewalProposalDtlsV mEResRenewalProposalDtlsV) {
        this.mEResRenewalProposalDtlsV = mEResRenewalProposalDtlsV;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mEResRenewalProposalDtlsV);
    }

    public int describeContents() {
        return  0;
    }

}
