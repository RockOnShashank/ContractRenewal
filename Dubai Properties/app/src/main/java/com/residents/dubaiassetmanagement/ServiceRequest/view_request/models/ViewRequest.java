
package com.residents.dubaiassetmanagement.ServiceRequest.view_request.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ViewRequest implements Parcelable
{

    @SerializedName("ServiceRequests")
    @Expose
    private List<ServiceRequest> serviceRequests = null;
    public final static Creator<ViewRequest> CREATOR = new Creator<ViewRequest>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ViewRequest createFromParcel(Parcel in) {
            return new ViewRequest(in);
        }

        public ViewRequest[] newArray(int size) {
            return (new ViewRequest[size]);
        }

    }
    ;

    protected ViewRequest(Parcel in) {
        in.readList(this.serviceRequests, (ServiceRequest.class.getClassLoader()));
    }

    public ViewRequest() {
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
