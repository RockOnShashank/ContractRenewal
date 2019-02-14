
package com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.models_follow_up;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowUp implements Parcelable
{

    @SerializedName("SRMemo")
    @Expose
    private SRMemo sRMemo;
    public final static Creator<FollowUp> CREATOR = new Creator<FollowUp>() {


        @SuppressWarnings({
            "unchecked"
        })
        public FollowUp createFromParcel(Parcel in) {
            return new FollowUp(in);
        }

        public FollowUp[] newArray(int size) {
            return (new FollowUp[size]);
        }

    }
    ;

    protected FollowUp(Parcel in) {
        this.sRMemo = ((SRMemo) in.readValue((SRMemo.class.getClassLoader())));
    }

    public FollowUp() {
    }

    public SRMemo getSRMemo() {
        return sRMemo;
    }

    public void setSRMemo(SRMemo sRMemo) {
        this.sRMemo = sRMemo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sRMemo);
    }

    public int describeContents() {
        return  0;
    }

}
