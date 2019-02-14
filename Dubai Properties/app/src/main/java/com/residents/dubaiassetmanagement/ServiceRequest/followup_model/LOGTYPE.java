
package com.residents.dubaiassetmanagement.ServiceRequest.followup_model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class LOGTYPE implements Parcelable
{

    public final static Creator<LOGTYPE> CREATOR = new Creator<LOGTYPE>() {


        @SuppressWarnings({
            "unchecked"
        })
        public LOGTYPE createFromParcel(Parcel in) {
            return new LOGTYPE(in);
        }

        public LOGTYPE[] newArray(int size) {
            return (new LOGTYPE[size]);
        }

    }
    ;

    protected LOGTYPE(Parcel in) {
    }

    public LOGTYPE() {
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return  0;
    }

}
