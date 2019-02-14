
package com.residents.dubaiassetmanagement.contract_renewal.review_proposal.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MELeaseChargesV implements Parcelable
{

    @SerializedName("Property_Code")
    @Expose
    private String propertyCode;
    @SerializedName("Charge_Code")
    @Expose
    private String chargeCode;
    @SerializedName("Charge_Description")
    @Expose
    private String chargeDescription;
    @SerializedName("Parameter")
    @Expose
    private String parameter;
    @SerializedName("Calculation_Basis")
    @Expose
    private Object calculationBasis;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("Event")
    @Expose
    private String event;
    public final static Creator<MELeaseChargesV> CREATOR = new Creator<MELeaseChargesV>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MELeaseChargesV createFromParcel(Parcel in) {
            return new MELeaseChargesV(in);
        }

        public MELeaseChargesV[] newArray(int size) {
            return (new MELeaseChargesV[size]);
        }

    }
    ;

    protected MELeaseChargesV(Parcel in) {
        this.propertyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.chargeCode = ((String) in.readValue((String.class.getClassLoader())));
        this.chargeDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.parameter = ((String) in.readValue((String.class.getClassLoader())));
        this.calculationBasis = ((Object) in.readValue((Object.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.event = ((String) in.readValue((String.class.getClassLoader())));
    }

    public MELeaseChargesV() {
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public String getChargeDescription() {
        return chargeDescription;
    }

    public void setChargeDescription(String chargeDescription) {
        this.chargeDescription = chargeDescription;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Object getCalculationBasis() {
        return calculationBasis;
    }

    public void setCalculationBasis(Object calculationBasis) {
        this.calculationBasis = calculationBasis;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(propertyCode);
        dest.writeValue(chargeCode);
        dest.writeValue(chargeDescription);
        dest.writeValue(parameter);
        dest.writeValue(calculationBasis);
        dest.writeValue(amount);
        dest.writeValue(event);
    }

    public int describeContents() {
        return  0;
    }

}
