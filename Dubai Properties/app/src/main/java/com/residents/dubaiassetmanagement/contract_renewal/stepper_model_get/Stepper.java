
package com.residents.dubaiassetmanagement.contract_renewal.stepper_model_get;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stepper implements Parcelable
{

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("BackAllowed")
    @Expose
    private Boolean backAllowed;
    @SerializedName("StepCompleted")
    @Expose
    private Boolean stepCompleted;
    public final static Creator<Stepper> CREATOR = new Creator<Stepper>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Stepper createFromParcel(Parcel in) {
            return new Stepper(in);
        }

        public Stepper[] newArray(int size) {
            return (new Stepper[size]);
        }

    }
    ;

    protected Stepper(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.backAllowed = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.stepCompleted = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Stepper() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getBackAllowed() {
        return backAllowed;
    }

    public void setBackAllowed(Boolean backAllowed) {
        this.backAllowed = backAllowed;
    }

    public Boolean getStepCompleted() {
        return stepCompleted;
    }

    public void setStepCompleted(Boolean stepCompleted) {
        this.stepCompleted = stepCompleted;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(description);
        dest.writeValue(backAllowed);
        dest.writeValue(stepCompleted);
    }

    public int describeContents() {
        return  0;
    }

}
