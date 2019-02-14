
package com.residents.dubaiassetmanagement.ServiceRequest.followup_model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ASSIGNREPLOCID implements Parcelable
{

    public final static Creator<ASSIGNREPLOCID> CREATOR = new Creator<ASSIGNREPLOCID>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ASSIGNREPLOCID createFromParcel(Parcel in) {
            return new ASSIGNREPLOCID(in);
        }

        public ASSIGNREPLOCID[] newArray(int size) {
            return (new ASSIGNREPLOCID[size]);
        }

    }
    ;

    protected ASSIGNREPLOCID(Parcel in) {
    }

    public ASSIGNREPLOCID() {
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return  0;
    }

}
