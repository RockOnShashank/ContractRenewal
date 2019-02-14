
package com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.models_follow_up;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SRMemo implements Parcelable
{

    @SerializedName("ME_Res_Workorder_Memo")
    @Expose
    private List<MEResWorkorderMemo> mEResWorkorderMemo = null;
    public final static Creator<SRMemo> CREATOR = new Creator<SRMemo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public SRMemo createFromParcel(Parcel in) {
            return new SRMemo(in);
        }

        public SRMemo[] newArray(int size) {
            return (new SRMemo[size]);
        }

    }
    ;

    protected SRMemo(Parcel in) {
        in.readList(this.mEResWorkorderMemo, (MEResWorkorderMemo.class.getClassLoader()));
    }

    public SRMemo() {
    }

    public List<MEResWorkorderMemo> getMEResWorkorderMemo() {
        return mEResWorkorderMemo;
    }

    public void setMEResWorkorderMemo(List<MEResWorkorderMemo> mEResWorkorderMemo) {
        this.mEResWorkorderMemo = mEResWorkorderMemo;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mEResWorkorderMemo);
    }

    public int describeContents() {
        return  0;
    }

}
