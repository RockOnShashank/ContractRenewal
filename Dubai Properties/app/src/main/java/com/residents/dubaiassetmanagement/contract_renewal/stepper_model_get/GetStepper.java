
package com.residents.dubaiassetmanagement.contract_renewal.stepper_model_get;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStepper implements Parcelable
{

    @SerializedName("Steppers")
    @Expose
    private List<Stepper> steppers = null;
    @SerializedName("ProposalId")
    @Expose
    private String proposalId;
    @SerializedName("IsShortTermRenewal")
    @Expose
    private Boolean isShortTermRenewal;
    @SerializedName("DeliveryDispatchedDate")
    @Expose
    private Object deliveryDispatchedDate;
    @SerializedName("DeliveryReceivedByTenantDate")
    @Expose
    private Object deliveryReceivedByTenantDate;
    @SerializedName("PickUpMadeDate")
    @Expose
    private String pickUpMadeDate;
    @SerializedName("PickUpReceivedByDubaiAMDate")
    @Expose
    private Object pickUpReceivedByDubaiAMDate;
    public final static Creator<GetStepper> CREATOR = new Creator<GetStepper>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GetStepper createFromParcel(Parcel in) {
            return new GetStepper(in);
        }

        public GetStepper[] newArray(int size) {
            return (new GetStepper[size]);
        }

    }
    ;

    protected GetStepper(Parcel in) {
        in.readList(this.steppers, (Stepper.class.getClassLoader()));
        this.proposalId = ((String) in.readValue((String.class.getClassLoader())));
        this.isShortTermRenewal = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.deliveryDispatchedDate = ((Object) in.readValue((Object.class.getClassLoader())));
        this.deliveryReceivedByTenantDate = ((Object) in.readValue((Object.class.getClassLoader())));
        this.pickUpMadeDate = ((String) in.readValue((String.class.getClassLoader())));
        this.pickUpReceivedByDubaiAMDate = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public GetStepper() {
    }

    public List<Stepper> getSteppers() {
        return steppers;
    }

    public void setSteppers(List<Stepper> steppers) {
        this.steppers = steppers;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public Boolean getIsShortTermRenewal() {
        return isShortTermRenewal;
    }

    public void setIsShortTermRenewal(Boolean isShortTermRenewal) {
        this.isShortTermRenewal = isShortTermRenewal;
    }

    public Object getDeliveryDispatchedDate() {
        return deliveryDispatchedDate;
    }

    public void setDeliveryDispatchedDate(Object deliveryDispatchedDate) {
        this.deliveryDispatchedDate = deliveryDispatchedDate;
    }

    public Object getDeliveryReceivedByTenantDate() {
        return deliveryReceivedByTenantDate;
    }

    public void setDeliveryReceivedByTenantDate(Object deliveryReceivedByTenantDate) {
        this.deliveryReceivedByTenantDate = deliveryReceivedByTenantDate;
    }

    public String getPickUpMadeDate() {
        return pickUpMadeDate;
    }

    public void setPickUpMadeDate(String pickUpMadeDate) {
        this.pickUpMadeDate = pickUpMadeDate;
    }

    public Object getPickUpReceivedByDubaiAMDate() {
        return pickUpReceivedByDubaiAMDate;
    }

    public void setPickUpReceivedByDubaiAMDate(Object pickUpReceivedByDubaiAMDate) {
        this.pickUpReceivedByDubaiAMDate = pickUpReceivedByDubaiAMDate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(steppers);
        dest.writeValue(proposalId);
        dest.writeValue(isShortTermRenewal);
        dest.writeValue(deliveryDispatchedDate);
        dest.writeValue(deliveryReceivedByTenantDate);
        dest.writeValue(pickUpMadeDate);
        dest.writeValue(pickUpReceivedByDubaiAMDate);
    }

    public int describeContents() {
        return  0;
    }

}
