
package com.residents.dubaiassetmanagement.home_screen.model_service_home;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceRequestHome implements Parcelable
{

    @SerializedName("ServiceRequests")
    @Expose
    private List<ServiceRequest> serviceRequests = null;
    public final static Creator<ServiceRequestHome> CREATOR = new Creator<ServiceRequestHome>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ServiceRequestHome createFromParcel(Parcel in) {
            return new ServiceRequestHome(in);
        }

        public ServiceRequestHome[] newArray(int size) {
            return (new ServiceRequestHome[size]);
        }

    }
    ;

    protected ServiceRequestHome(Parcel in) {
        in.readList(this.serviceRequests, (ServiceRequest.class.getClassLoader()));
    }

    public ServiceRequestHome() {
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
