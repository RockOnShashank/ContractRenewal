
package com.residents.dubaiassetmanagement.contract_renewal.confirm_details.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PdcResponse implements Parcelable
{

    @SerializedName("PdcList")
    @Expose
    private List<PdcList> pdcList = null;
    public final static Creator<PdcResponse> CREATOR = new Creator<PdcResponse>() {


        @SuppressWarnings({
            "unchecked"
        })
        public PdcResponse createFromParcel(Parcel in) {
            return new PdcResponse(in);
        }

        public PdcResponse[] newArray(int size) {
            return (new PdcResponse[size]);
        }

    }
    ;

    protected PdcResponse(Parcel in) {
        in.readList(this.pdcList, (PdcList.class.getClassLoader()));
    }

    public PdcResponse() {
    }

    public List<PdcList> getPdcList() {
        return pdcList;
    }

    public void setPdcList(List<PdcList> pdcList) {
        this.pdcList = pdcList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(pdcList);
    }

    public int describeContents() {
        return  0;
    }

}
