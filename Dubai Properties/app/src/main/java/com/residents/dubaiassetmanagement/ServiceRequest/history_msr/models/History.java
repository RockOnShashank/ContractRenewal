
package com.residents.dubaiassetmanagement.ServiceRequest.history_msr.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History implements Parcelable
{

    @SerializedName("ServiceRequests")
    @Expose
    private List<ServiceRequest> serviceRequests = null;
    public final static Creator<History> CREATOR = new Creator<History>() {


        @SuppressWarnings({
            "unchecked"
        })
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        public History[] newArray(int size) {
            return (new History[size]);
        }

    }
    ;

    protected History(Parcel in) {
        in.readList(this.serviceRequests, (ServiceRequest.class.getClassLoader()));
    }

    public History() {
    }

    public List<ServiceRequest> getServiceRequests() {
        return serviceRequests;
    }

    public void setServiceRequests(List<ServiceRequest> serviceRequests) {
        this.serviceRequests = serviceRequests;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(serviceRequests);
    }

    public int describeContents() {
        return  0;
    }

}
