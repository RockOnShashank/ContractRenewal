
package com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.models_follow_up;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MEResWorkorderMemo implements Parcelable
{

    @SerializedName("Tenant_Code")
    @Expose
    private String tenantCode;
    @SerializedName("Work_Order_ID")
    @Expose
    private String workOrderID;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("Status")
    @Expose
    private Object status;
    @SerializedName("Type")
    @Expose
    private Object type;
    @SerializedName("Result")
    @Expose
    private Object result;
    @SerializedName("Agents")
    @Expose
    private Object agents;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("Property")
    @Expose
    private String property;
    @SerializedName("Unit")
    @Expose
    private Object unit;
    @SerializedName("Employee")
    @Expose
    private Object employee;
    @SerializedName("Memo_ID")
    @Expose
    private String memoID;
    public final static Creator<MEResWorkorderMemo> CREATOR = new Creator<MEResWorkorderMemo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MEResWorkorderMemo createFromParcel(Parcel in) {
            return new MEResWorkorderMemo(in);
        }

        public MEResWorkorderMemo[] newArray(int size) {
            return (new MEResWorkorderMemo[size]);
        }

    }
    ;

    protected MEResWorkorderMemo(Parcel in) {
        this.tenantCode = ((String) in.readValue((String.class.getClassLoader())));
        this.workOrderID = ((String) in.readValue((String.class.getClassLoader())));
        this.date = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((Object) in.readValue((Object.class.getClassLoader())));
        this.type = ((Object) in.readValue((Object.class.getClassLoader())));
        this.result = ((Object) in.readValue((Object.class.getClassLoader())));
        this.agents = ((Object) in.readValue((Object.class.getClassLoader())));
        this.notes = ((String) in.readValue((String.class.getClassLoader())));
        this.property = ((String) in.readValue((String.class.getClassLoader())));
        this.unit = ((Object) in.readValue((Object.class.getClassLoader())));
        this.employee = ((Object) in.readValue((Object.class.getClassLoader())));
        this.memoID = ((String) in.readValue((String.class.getClassLoader())));
    }

    public MEResWorkorderMemo() {
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getWorkOrderID() {
        return workOrderID;
    }

    public void setWorkOrderID(String workOrderID) {
        this.workOrderID = workOrderID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getAgents() {
        return agents;
    }

    public void setAgents(Object agents) {
        this.agents = agents;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Object getUnit() {
        return unit;
    }

    public void setUnit(Object unit) {
        this.unit = unit;
    }

    public Object getEmployee() {
        return employee;
    }

    public void setEmployee(Object employee) {
        this.employee = employee;
    }

    public String getMemoID() {
        return memoID;
    }

    public void setMemoID(String memoID) {
        this.memoID = memoID;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(tenantCode);
        dest.writeValue(workOrderID);
        dest.writeValue(date);
        dest.writeValue(status);
        dest.writeValue(type);
        dest.writeValue(result);
        dest.writeValue(agents);
        dest.writeValue(notes);
        dest.writeValue(property);
        dest.writeValue(unit);
        dest.writeValue(employee);
        dest.writeValue(memoID);
    }

    public int describeContents() {
        return  0;
    }

}
