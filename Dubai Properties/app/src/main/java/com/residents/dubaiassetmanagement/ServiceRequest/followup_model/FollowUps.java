
package com.residents.dubaiassetmanagement.ServiceRequest.followup_model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowUps implements Parcelable
{

    @SerializedName("MaximoMemo")
    @Expose
    private List<MaximoMemo> maximoMemo = null;
    public final static Creator<FollowUps> CREATOR = new Creator<FollowUps>() {


        @SuppressWarnings({
            "unchecked"
        })
        public FollowUps createFromParcel(Parcel in) {
            return new FollowUps(in);
        }

        public FollowUps[] newArray(int size) {
            return (new FollowUps[size]);
        }

    }
    ;

    protected FollowUps(Parcel in) {
        in.readList(this.maximoMemo, (MaximoMemo.class.getClassLoader()));
    }

    public FollowUps() {
    }

    public List<MaximoMemo> getMaximoMemo() {
        return maximoMemo;
    }

    public void setMaximoMemo(List<MaximoMemo> maximoMemo) {
        this.maximoMemo = maximoMemo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(maximoMemo);
    }

    public int describeContents() {
        return  0;
    }

}
