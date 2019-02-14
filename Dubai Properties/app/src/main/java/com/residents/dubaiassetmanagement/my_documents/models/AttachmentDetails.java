
package com.residents.dubaiassetmanagement.my_documents.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttachmentDetails implements Parcelable
{

    @SerializedName("DPG_Attachment_Details_V")
    @Expose
    private List<DPGAttachmentDetailsV> dPGAttachmentDetailsV = null;
    public final static Creator<AttachmentDetails> CREATOR = new Creator<AttachmentDetails>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AttachmentDetails createFromParcel(Parcel in) {
            return new AttachmentDetails(in);
        }

        public AttachmentDetails[] newArray(int size) {
            return (new AttachmentDetails[size]);
        }

    }
    ;

    protected AttachmentDetails(Parcel in) {
        in.readList(this.dPGAttachmentDetailsV, (DPGAttachmentDetailsV.class.getClassLoader()));
    }

    public AttachmentDetails() {
    }

    public List<DPGAttachmentDetailsV> getDPGAttachmentDetailsV() {
        return dPGAttachmentDetailsV;
    }

    public void setDPGAttachmentDetailsV(List<DPGAttachmentDetailsV> dPGAttachmentDetailsV) {
        this.dPGAttachmentDetailsV = dPGAttachmentDetailsV;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(dPGAttachmentDetailsV);
    }

    public int describeContents() {
        return  0;
    }

}
