
package com.residents.dubaiassetmanagement.contract_renewal.review_proposal.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MEResRenewalProposalDtlsV implements Parcelable
{

    @SerializedName("TCODE")
    @Expose
    private String tCODE;
    @SerializedName("PROPOSAL_ID")
    @Expose
    private String pROPOSALID;
    @SerializedName("PROPOSAL_START_DATE")
    @Expose
    private String pROPOSALSTARTDATE;
    @SerializedName("PROPOSAL_END_DATE")
    @Expose
    private String pROPOSALENDDATE;
    @SerializedName("RENEWAL_TERM")
    @Expose
    private String rENEWALTERM;
    @SerializedName("RENEWAL_RENT")
    @Expose
    private String rENEWALRENT;
    @SerializedName("INVOICE_FREQUENCY")
    @Expose
    private String iNVOICEFREQUENCY;
    @SerializedName("EXISTING_RENT")
    @Expose
    private String eXISTINGRENT;
    @SerializedName("PERCENTAGE_CHANGE")
    @Expose
    private String pERCENTAGECHANGE;
    public final static Creator<MEResRenewalProposalDtlsV> CREATOR = new Creator<MEResRenewalProposalDtlsV>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MEResRenewalProposalDtlsV createFromParcel(Parcel in) {
            return new MEResRenewalProposalDtlsV(in);
        }

        public MEResRenewalProposalDtlsV[] newArray(int size) {
            return (new MEResRenewalProposalDtlsV[size]);
        }

    }
    ;

    protected MEResRenewalProposalDtlsV(Parcel in) {
        this.tCODE = ((String) in.readValue((String.class.getClassLoader())));
        this.pROPOSALID = ((String) in.readValue((String.class.getClassLoader())));
        this.pROPOSALSTARTDATE = ((String) in.readValue((String.class.getClassLoader())));
        this.pROPOSALENDDATE = ((String) in.readValue((String.class.getClassLoader())));
        this.rENEWALTERM = ((String) in.readValue((String.class.getClassLoader())));
        this.rENEWALRENT = ((String) in.readValue((String.class.getClassLoader())));
        this.iNVOICEFREQUENCY = ((String) in.readValue((String.class.getClassLoader())));
        this.eXISTINGRENT = ((String) in.readValue((String.class.getClassLoader())));
        this.pERCENTAGECHANGE = ((String) in.readValue((String.class.getClassLoader())));
    }

    public MEResRenewalProposalDtlsV() {
    }

    public String getTCODE() {
        return tCODE;
    }

    public void setTCODE(String tCODE) {
        this.tCODE = tCODE;
    }

    public String getPROPOSALID() {
        return pROPOSALID;
    }

    public void setPROPOSALID(String pROPOSALID) {
        this.pROPOSALID = pROPOSALID;
    }

    public String getPROPOSALSTARTDATE() {
        return pROPOSALSTARTDATE;
    }

    public void setPROPOSALSTARTDATE(String pROPOSALSTARTDATE) {
        this.pROPOSALSTARTDATE = pROPOSALSTARTDATE;
    }

    public String getPROPOSALENDDATE() {
        return pROPOSALENDDATE;
    }

    public void setPROPOSALENDDATE(String pROPOSALENDDATE) {
        this.pROPOSALENDDATE = pROPOSALENDDATE;
    }

    public String getRENEWALTERM() {
        return rENEWALTERM;
    }

    public void setRENEWALTERM(String rENEWALTERM) {
        this.rENEWALTERM = rENEWALTERM;
    }

    public String getRENEWALRENT() {
        return rENEWALRENT;
    }

    public void setRENEWALRENT(String rENEWALRENT) {
        this.rENEWALRENT = rENEWALRENT;
    }

    public String getINVOICEFREQUENCY() {
        return iNVOICEFREQUENCY;
    }

    public void setINVOICEFREQUENCY(String iNVOICEFREQUENCY) {
        this.iNVOICEFREQUENCY = iNVOICEFREQUENCY;
    }

    public String getEXISTINGRENT() {
        return eXISTINGRENT;
    }

    public void setEXISTINGRENT(String eXISTINGRENT) {
        this.eXISTINGRENT = eXISTINGRENT;
    }

    public String getPERCENTAGECHANGE() {
        return pERCENTAGECHANGE;
    }

    public void setPERCENTAGECHANGE(String pERCENTAGECHANGE) {
        this.pERCENTAGECHANGE = pERCENTAGECHANGE;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(tCODE);
        dest.writeValue(pROPOSALID);
        dest.writeValue(pROPOSALSTARTDATE);
        dest.writeValue(pROPOSALENDDATE);
        dest.writeValue(rENEWALTERM);
        dest.writeValue(rENEWALRENT);
        dest.writeValue(iNVOICEFREQUENCY);
        dest.writeValue(eXISTINGRENT);
        dest.writeValue(pERCENTAGECHANGE);
    }

    public int describeContents() {
        return  0;
    }

}
