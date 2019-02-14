
package com.residents.dubaiassetmanagement.my_documents.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyDocuments implements Parcelable
{

    @SerializedName("Attachment_Details")
    @Expose
    private AttachmentDetails attachmentDetails;
    public final static Creator<MyDocuments> CREATOR = new Creator<MyDocuments>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MyDocuments createFromParcel(Parcel in) {
            return new MyDocuments(in);
        }

        public MyDocuments[] newArray(int size) {
            return (new MyDocuments[size]);
        }

    }
    ;

    protected MyDocuments(Parcel in) {
        this.attachmentDetails = ((AttachmentDetails) in.readValue((AttachmentDetails.class.getClassLoader())));
    }

    public MyDocuments() {
    }

    public AttachmentDetails getAttachmentDetails() {
        return attachmentDetails;
    }

    public void setAttachmentDetails(AttachmentDetails attachmentDetails) {
        this.attachmentDetails = attachmentDetails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(attachmentDetails);
    }

    public int describeContents() {
        return  0;
    }

}
