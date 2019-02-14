
package com.residents.dubaiassetmanagement.my_documents.models_status;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceRequest implements Parcelable
{

    @SerializedName("Tenant_Code")
    @Expose
    private String tenantCode;
    @SerializedName("WO_Code")
    @Expose
    private String wOCode;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Sub_Category")
    @Expose
    private String subCategory;
    @SerializedName("Problem_Description")
    @Expose
    private String problemDescription;
    @SerializedName("Caller_Name")
    @Expose
    private String callerName;
    @SerializedName("Caller_Phone")
    @Expose
    private String callerPhone;
    @SerializedName("Call_Date")
    @Expose
    private String callDate;
    @SerializedName("Completion_Date")
    @Expose
    private Object completionDate;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("Related_Wo")
    @Expose
    private String relatedWo;
    @SerializedName("Maximo_ID")
    @Expose
    private Object maximoID;
    @SerializedName("Origin")
    @Expose
    private String origin;
    @SerializedName("Resolution")
    @Expose
    private Object resolution;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Unit_Code")
    @Expose
    private String unitCode;
    @SerializedName("Template_Code")
    @Expose
    private String templateCode;
    @SerializedName("WO_Priority")
    @Expose
    private String wOPriority;
    @SerializedName("Maximo_Site_ID")
    @Expose
    private Object maximoSiteID;
    @SerializedName("WF_Status")
    @Expose
    private String wFStatus;
    @SerializedName("Preferred_Date")
    @Expose
    private Object preferredDate;
    @SerializedName("Preferred_TimeSlot")
    @Expose
    private Object preferredTimeSlot;
    @SerializedName("TenantResponsibilityItem")
    @Expose
    private Boolean tenantResponsibilityItem;
    @SerializedName("SLADateTime")
    @Expose
    private Object sLADateTime;
    @SerializedName("LastFollowupDate")
    @Expose
    private Object lastFollowupDate;
    @SerializedName("ReopenComments")
    @Expose
    private Object reopenComments;
    @SerializedName("ServiceRequestType")
    @Expose
    private Object serviceRequestType;
    @SerializedName("ViewMore")
    @Expose
    private Object viewMore;
    @SerializedName("isEmergency")
    @Expose
    private Object isEmergency;
    @SerializedName("isCancellable")
    @Expose
    private Boolean isCancellable;
    public final static Creator<ServiceRequest> CREATOR = new Creator<ServiceRequest>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ServiceRequest createFromParcel(Parcel in) {
            return new ServiceRequest(in);
        }

        public ServiceRequest[] newArray(int size) {
            return (new ServiceRequest[size]);
        }

    }
    ;

    protected ServiceRequest(Parcel in) {
        this.tenantCode = ((String) in.readValue((String.class.getClassLoader())));
        this.wOCode = ((String) in.readValue((String.class.getClassLoader())));
        this.category = ((String) in.readValue((String.class.getClassLoader())));
        this.subCategory = ((String) in.readValue((String.class.getClassLoader())));
        this.problemDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.callerName = ((String) in.readValue((String.class.getClassLoader())));
        this.callerPhone = ((String) in.readValue((String.class.getClassLoader())));
        this.callDate = ((String) in.readValue((String.class.getClassLoader())));
        this.completionDate = ((Object) in.readValue((Object.class.getClassLoader())));
        this.location = ((String) in.readValue((String.class.getClassLoader())));
        this.relatedWo = ((String) in.readValue((String.class.getClassLoader())));
        this.maximoID = ((Object) in.readValue((Object.class.getClassLoader())));
        this.origin = ((String) in.readValue((String.class.getClassLoader())));
        this.resolution = ((Object) in.readValue((Object.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.unitCode = ((String) in.readValue((String.class.getClassLoader())));
        this.templateCode = ((String) in.readValue((String.class.getClassLoader())));
        this.wOPriority = ((String) in.readValue((String.class.getClassLoader())));
        this.maximoSiteID = ((Object) in.readValue((Object.class.getClassLoader())));
        this.wFStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.preferredDate = ((Object) in.readValue((Object.class.getClassLoader())));
        this.preferredTimeSlot = ((Object) in.readValue((Object.class.getClassLoader())));
        this.tenantResponsibilityItem = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.sLADateTime = ((Object) in.readValue((Object.class.getClassLoader())));
        this.lastFollowupDate = ((Object) in.readValue((Object.class.getClassLoader())));
        this.reopenComments = ((Object) in.readValue((Object.class.getClassLoader())));
        this.serviceRequestType = ((Object) in.readValue((Object.class.getClassLoader())));
        this.viewMore = ((Object) in.readValue((Object.class.getClassLoader())));
        this.isEmergency = ((Object) in.readValue((Object.class.getClassLoader())));
        this.isCancellable = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public ServiceRequest() {
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getWOCode() {
        return wOCode;
    }

    public void setWOCode(String wOCode) {
        this.wOCode = wOCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public String getCallerPhone() {
        return callerPhone;
    }

    public void setCallerPhone(String callerPhone) {
        this.callerPhone = callerPhone;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public Object getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Object completionDate) {
        this.completionDate = completionDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRelatedWo() {
        return relatedWo;
    }

    public void setRelatedWo(String relatedWo) {
        this.relatedWo = relatedWo;
    }

    public Object getMaximoID() {
        return maximoID;
    }

    public void setMaximoID(Object maximoID) {
        this.maximoID = maximoID;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Object getResolution() {
        return resolution;
    }

    public void setResolution(Object resolution) {
        this.resolution = resolution;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getWOPriority() {
        return wOPriority;
    }

    public void setWOPriority(String wOPriority) {
        this.wOPriority = wOPriority;
    }

    public Object getMaximoSiteID() {
        return maximoSiteID;
    }

    public void setMaximoSiteID(Object maximoSiteID) {
        this.maximoSiteID = maximoSiteID;
    }

    public String getWFStatus() {
        return wFStatus;
    }

    public void setWFStatus(String wFStatus) {
        this.wFStatus = wFStatus;
    }

    public Object getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(Object preferredDate) {
        this.preferredDate = preferredDate;
    }

    public Object getPreferredTimeSlot() {
        return preferredTimeSlot;
    }

    public void setPreferredTimeSlot(Object preferredTimeSlot) {
        this.preferredTimeSlot = preferredTimeSlot;
    }

    public Boolean getTenantResponsibilityItem() {
        return tenantResponsibilityItem;
    }

    public void setTenantResponsibilityItem(Boolean tenantResponsibilityItem) {
        this.tenantResponsibilityItem = tenantResponsibilityItem;
    }

    public Object getSLADateTime() {
        return sLADateTime;
    }

    public void setSLADateTime(Object sLADateTime) {
        this.sLADateTime = sLADateTime;
    }

    public Object getLastFollowupDate() {
        return lastFollowupDate;
    }

    public void setLastFollowupDate(Object lastFollowupDate) {
        this.lastFollowupDate = lastFollowupDate;
    }

    public Object getReopenComments() {
        return reopenComments;
    }

    public void setReopenComments(Object reopenComments) {
        this.reopenComments = reopenComments;
    }

    public Object getServiceRequestType() {
        return serviceRequestType;
    }

    public void setServiceRequestType(Object serviceRequestType) {
        this.serviceRequestType = serviceRequestType;
    }

    public Object getViewMore() {
        return viewMore;
    }

    public void setViewMore(Object viewMore) {
        this.viewMore = viewMore;
    }

    public Object getIsEmergency() {
        return isEmergency;
    }

    public void setIsEmergency(Object isEmergency) {
        this.isEmergency = isEmergency;
    }

    public Boolean getIsCancellable() {
        return isCancellable;
    }

    public void setIsCancellable(Boolean isCancellable) {
        this.isCancellable = isCancellable;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(tenantCode);
        dest.writeValue(wOCode);
        dest.writeValue(category);
        dest.writeValue(subCategory);
        dest.writeValue(problemDescription);
        dest.writeValue(callerName);
        dest.writeValue(callerPhone);
        dest.writeValue(callDate);
        dest.writeValue(completionDate);
        dest.writeValue(location);
        dest.writeValue(relatedWo);
        dest.writeValue(maximoID);
        dest.writeValue(origin);
        dest.writeValue(resolution);
        dest.writeValue(status);
        dest.writeValue(unitCode);
        dest.writeValue(templateCode);
        dest.writeValue(wOPriority);
        dest.writeValue(maximoSiteID);
        dest.writeValue(wFStatus);
        dest.writeValue(preferredDate);
        dest.writeValue(preferredTimeSlot);
        dest.writeValue(tenantResponsibilityItem);
        dest.writeValue(sLADateTime);
        dest.writeValue(lastFollowupDate);
        dest.writeValue(reopenComments);
        dest.writeValue(serviceRequestType);
        dest.writeValue(viewMore);
        dest.writeValue(isEmergency);
        dest.writeValue(isCancellable);
    }

    public int describeContents() {
        return  0;
    }

}