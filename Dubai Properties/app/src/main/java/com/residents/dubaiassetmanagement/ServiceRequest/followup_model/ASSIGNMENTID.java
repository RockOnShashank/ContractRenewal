
package com.residents.dubaiassetmanagement.ServiceRequest.followup_model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ASSIGNMENTID implements Parcelable
{

    public final static Creator<ASSIGNMENTID> CREATOR = new Creator<ASSIGNMENTID>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ASSIGNMENTID createFromParcel(Parcel in) {
            return new ASSIGNMENTID(in);
        }

        public ASSIGNMENTID[] newArray(int size) {
            return (new ASSIGNMENTID[size]);
        }

    }
    ;

    protected ASSIGNMENTID(Parcel in) {
    }

    public ASSIGNMENTID() {
    }

    public void writeToParcel(Parcel dest, int flags) {
    }

    public int describeContents() {
        return  0;
    }

}
