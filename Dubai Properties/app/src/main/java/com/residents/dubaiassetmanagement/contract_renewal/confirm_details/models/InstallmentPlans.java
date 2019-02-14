
package com.residents.dubaiassetmanagement.contract_renewal.confirm_details.models;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstallmentPlans implements Parcelable
{

    @SerializedName("InstallmentPlans")
    @Expose
    private List<InstallmentPlan> installmentPlans = null;
    public final static Creator<InstallmentPlans> CREATOR = new Creator<InstallmentPlans>() {


        @SuppressWarnings({
            "unchecked"
        })
        public InstallmentPlans createFromParcel(Parcel in) {
            return new InstallmentPlans(in);
        }

        public InstallmentPlans[] newArray(int size) {
            return (new InstallmentPlans[size]);
        }

    }
    ;

    protected InstallmentPlans(Parcel in) {
        in.readList(this.installmentPlans, (InstallmentPlan.class.getClassLoader()));
    }

    public InstallmentPlans() {
    }

    public List<InstallmentPlan> getInstallmentPlans() {
        return installmentPlans;
    }

    public void setInstallmentPlans(List<InstallmentPlan> installmentPlans) {
        this.installmentPlans = installmentPlans;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(installmentPlans);
    }

    public int describeContents() {
        return  0;
    }

}
