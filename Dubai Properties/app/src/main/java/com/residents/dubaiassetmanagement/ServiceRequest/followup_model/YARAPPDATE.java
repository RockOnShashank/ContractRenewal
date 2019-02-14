
package com.residents.dubaiassetmanagement.ServiceRequest.followup_model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class YARAPPDATE implements Parcelable
{

    public final static Creator<YARAPPDATE> CREATOR = new Creator<YARAPPDATE>() {


        @SuppressWarnings({
            "unchecked"
        })
        public YARAPPDATE createFromParcel(Parcel in) {
            return new YARAPPDATE(in);
        }

        public YARAPPDATE[] newArray(int size) {
            return (new YARAPPDATE[size]);
        }

    }
    ;

    protected YARAPPDATE(Parcel in) {
    }

    public YARAPPDATE() {
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return  0;
    }

}
