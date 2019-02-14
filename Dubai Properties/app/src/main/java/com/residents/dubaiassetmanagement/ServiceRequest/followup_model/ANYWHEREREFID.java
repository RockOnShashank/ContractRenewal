
package com.residents.dubaiassetmanagement.ServiceRequest.followup_model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ANYWHEREREFID implements Parcelable
{

    public final static Creator<ANYWHEREREFID> CREATOR = new Creator<ANYWHEREREFID>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ANYWHEREREFID createFromParcel(Parcel in) {
            return new ANYWHEREREFID(in);
        }

        public ANYWHEREREFID[] newArray(int size) {
            return (new ANYWHEREREFID[size]);
        }

    }
    ;

    protected ANYWHEREREFID(Parcel in) {
    }

    public ANYWHEREREFID() {
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return  0;
    }

}
