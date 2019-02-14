
package com.residents.dubaiassetmanagement.my_documents.models_status;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocumentsStatus implements Parcelable
{

    @SerializedName("ServiceRequest")
    @Expose
    private List<ServiceRequest> serviceRequest = null;
    @SerializedName("MaximoDetails")
    @Expose
    private Object maximoDetails;
    public final static Creator<DocumentsStatus> CREATOR = new Creator<DocumentsStatus>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DocumentsStatus createFromParcel(Parcel in) {
            return new DocumentsStatus(in);
        }

        public DocumentsStatus[] newArray(int size) {
            return (new DocumentsStatus[size]);
        }

    }
    ;

    protected DocumentsStatus(Parcel in) {
        in.readList(this.serviceRequest, (ServiceRequest.class.getClassLoader()));
        this.maximoDetails = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public DocumentsStatus() {
    }

    public List<ServiceRequest> getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(List<ServiceRequest> serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public Object getMaximoDetails() {
        return maximoDetails;
    }

    public void setMaximoDetails(Object maximoDetails) {
        this.maximoDetails = maximoDetails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(serviceRequest);
        dest.writeValue(maximoDetails);
    }

    public int describeContents() {
        return  0;
    }

}
