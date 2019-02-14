
package com.residents.dubaiassetmanagement.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TenantDetail implements Parcelable
{

    @SerializedName("Tenant_Name")
    @Expose
    private String tenantName;
    @SerializedName("Tenant_Code")
    @Expose
    private String tenantCode;
    @SerializedName("Tenant_Email")
    @Expose
    private String tenantEmail;
    @SerializedName("Tenant_Mobile")
    @Expose
    private String tenantMobile;
    @SerializedName("Tenant_Project_Name")
    @Expose
    private String tenantProjectName;
    @SerializedName("Tenant_First_Name")
    @Expose
    private String tenantFirstName;
    @SerializedName("Tenant_Last_Name")
    @Expose
    private String tenantLastName;
    @SerializedName("Tenant_Dob")
    @Expose
    private String tenantDob;
    @SerializedName("Tenant_Nationality")
    @Expose
    private String tenantNationality;
    @SerializedName("Tenant_Visa_Number")
    @Expose
    private String tenantVisaNumber;
    @SerializedName("Tenant_Visa_Exp_Date")
    @Expose
    private String tenantVisaExpDate;
    @SerializedName("Tenant_Emirates_Id")
    @Expose
    private String tenantEmiratesId;
    @SerializedName("Tenant_Emiratesid_Exp_Date")
    @Expose
    private String tenantEmiratesidExpDate;
    @SerializedName("Tenant_Passport_Number")
    @Expose
    private String tenantPassportNumber;
    @SerializedName("Tenant_Passport_Exp_Date")
    @Expose
    private String tenantPassportExpDate;
    @SerializedName("Tenant_Alt_Mob_No")
    @Expose
    private String tenantAltMobNo;
    @SerializedName("Tenant_Landline_No")
    @Expose
    private String tenantLandlineNo;
    @SerializedName("Tenant_Emergency_No")
    @Expose
    private String tenantEmergencyNo;
    @SerializedName("Tenant_Zip")
    @Expose
    private String tenantZip;
    @SerializedName("Company_Name")
    @Expose
    private String companyName;
    @SerializedName("Company_Addr")
    @Expose
    private String companyAddr;
    @SerializedName("Property_Code")
    @Expose
    private String propertyCode;
    @SerializedName("Unit_Code")
    @Expose
    private String unitCode;
    @SerializedName("Lease_Term")
    @Expose
    private String leaseTerm;
    @SerializedName("Lease_Type")
    @Expose
    private String leaseType;
    @SerializedName("Lease_Start_Date")
    @Expose
    private String leaseStartDate;
    @SerializedName("Lease_End_Date")
    @Expose
    private String leaseEndDate;
    @SerializedName("Payment_Terms")
    @Expose
    private String paymentTerms;
    @SerializedName("Lease_Amount")
    @Expose
    private String leaseAmount;
    @SerializedName("Tenant_Status")
    @Expose
    private String tenantStatus;
    @SerializedName("Dewa_Premise_Number")
    @Expose
    private String dewaPremiseNumber;
    public final static Creator<TenantDetail> CREATOR = new Creator<TenantDetail>() {


        @SuppressWarnings({
            "unchecked"
        })
        public TenantDetail createFromParcel(Parcel in) {
            return new TenantDetail(in);
        }

        public TenantDetail[] newArray(int size) {
            return (new TenantDetail[size]);
        }

    }
    ;

    protected TenantDetail(Parcel in) {
        this.tenantName = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantCode = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantEmail = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantMobile = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantProjectName = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantFirstName = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantLastName = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantDob = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantNationality = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantVisaNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantVisaExpDate = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantEmiratesId = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantEmiratesidExpDate = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantPassportNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantPassportExpDate = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantAltMobNo = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantLandlineNo = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantEmergencyNo = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantZip = ((String) in.readValue((String.class.getClassLoader())));
        this.companyName = ((String) in.readValue((String.class.getClassLoader())));
        this.companyAddr = ((String) in.readValue((String.class.getClassLoader())));
        this.propertyCode = ((String) in.readValue((String.class.getClassLoader())));
        this.unitCode = ((String) in.readValue((String.class.getClassLoader())));
        this.leaseTerm = ((String) in.readValue((String.class.getClassLoader())));
        this.leaseType = ((String) in.readValue((String.class.getClassLoader())));
        this.leaseStartDate = ((String) in.readValue((String.class.getClassLoader())));
        this.leaseEndDate = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentTerms = ((String) in.readValue((String.class.getClassLoader())));
        this.leaseAmount = ((String) in.readValue((String.class.getClassLoader())));
        this.tenantStatus = ((String) in.readValue((String.class.getClassLoader())));
        this.dewaPremiseNumber = ((String) in.readValue((String.class.getClassLoader())));
    }

    public TenantDetail() {
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getTenantEmail() {
        return tenantEmail;
    }

    public void setTenantEmail(String tenantEmail) {
        this.tenantEmail = tenantEmail;
    }

    public String getTenantMobile() {
        return tenantMobile;
    }

    public void setTenantMobile(String tenantMobile) {
        this.tenantMobile = tenantMobile;
    }

    public String getTenantProjectName() {
        return tenantProjectName;
    }

    public void setTenantProjectName(String tenantProjectName) {
        this.tenantProjectName = tenantProjectName;
    }

    public String getTenantFirstName() {
        return tenantFirstName;
    }

    public void setTenantFirstName(String tenantFirstName) {
        this.tenantFirstName = tenantFirstName;
    }

    public String getTenantLastName() {
        return tenantLastName;
    }

    public void setTenantLastName(String tenantLastName) {
        this.tenantLastName = tenantLastName;
    }

    public String getTenantDob() {
        return tenantDob;
    }

    public void setTenantDob(String tenantDob) {
        this.tenantDob = tenantDob;
    }

    public String getTenantNationality() {
        return tenantNationality;
    }

    public void setTenantNationality(String tenantNationality) {
        this.tenantNationality = tenantNationality;
    }

    public String getTenantVisaNumber() {
        return tenantVisaNumber;
    }

    public void setTenantVisaNumber(String tenantVisaNumber) {
        this.tenantVisaNumber = tenantVisaNumber;
    }

    public String getTenantVisaExpDate() {
        return tenantVisaExpDate;
    }

    public void setTenantVisaExpDate(String tenantVisaExpDate) {
        this.tenantVisaExpDate = tenantVisaExpDate;
    }

    public String getTenantEmiratesId() {
        return tenantEmiratesId;
    }

    public void setTenantEmiratesId(String tenantEmiratesId) {
        this.tenantEmiratesId = tenantEmiratesId;
    }

    public String getTenantEmiratesidExpDate() {
        return tenantEmiratesidExpDate;
    }

    public void setTenantEmiratesidExpDate(String tenantEmiratesidExpDate) {
        this.tenantEmiratesidExpDate = tenantEmiratesidExpDate;
    }

    public String getTenantPassportNumber() {
        return tenantPassportNumber;
    }

    public void setTenantPassportNumber(String tenantPassportNumber) {
        this.tenantPassportNumber = tenantPassportNumber;
    }

    public String getTenantPassportExpDate() {
        return tenantPassportExpDate;
    }

    public void setTenantPassportExpDate(String tenantPassportExpDate) {
        this.tenantPassportExpDate = tenantPassportExpDate;
    }

    public String getTenantAltMobNo() {
        return tenantAltMobNo;
    }

    public void setTenantAltMobNo(String tenantAltMobNo) {
        this.tenantAltMobNo = tenantAltMobNo;
    }

    public String getTenantLandlineNo() {
        return tenantLandlineNo;
    }

    public void setTenantLandlineNo(String tenantLandlineNo) {
        this.tenantLandlineNo = tenantLandlineNo;
    }

    public String getTenantEmergencyNo() {
        return tenantEmergencyNo;
    }

    public void setTenantEmergencyNo(String tenantEmergencyNo) {
        this.tenantEmergencyNo = tenantEmergencyNo;
    }

    public String getTenantZip() {
        return tenantZip;
    }

    public void setTenantZip(String tenantZip) {
        this.tenantZip = tenantZip;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getLeaseTerm() {
        return leaseTerm;
    }

    public void setLeaseTerm(String leaseTerm) {
        this.leaseTerm = leaseTerm;
    }

    public String getLeaseType() {
        return leaseType;
    }

    public void setLeaseType(String leaseType) {
        this.leaseType = leaseType;
    }

    public String getLeaseStartDate() {
        return leaseStartDate;
    }

    public void setLeaseStartDate(String leaseStartDate) {
        this.leaseStartDate = leaseStartDate;
    }

    public String getLeaseEndDate() {
        return leaseEndDate;
    }

    public void setLeaseEndDate(String leaseEndDate) {
        this.leaseEndDate = leaseEndDate;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getLeaseAmount() {
        return leaseAmount;
    }

    public void setLeaseAmount(String leaseAmount) {
        this.leaseAmount = leaseAmount;
    }

    public String getTenantStatus() {
        return tenantStatus;
    }

    public void setTenantStatus(String tenantStatus) {
        this.tenantStatus = tenantStatus;
    }

    public String getDewaPremiseNumber() {
        return dewaPremiseNumber;
    }

    public void setDewaPremiseNumber(String dewaPremiseNumber) {
        this.dewaPremiseNumber = dewaPremiseNumber;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(tenantName);
        dest.writeValue(tenantCode);
        dest.writeValue(tenantEmail);
        dest.writeValue(tenantMobile);
        dest.writeValue(tenantProjectName);
        dest.writeValue(tenantFirstName);
        dest.writeValue(tenantLastName);
        dest.writeValue(tenantDob);
        dest.writeValue(tenantNationality);
        dest.writeValue(tenantVisaNumber);
        dest.writeValue(tenantVisaExpDate);
        dest.writeValue(tenantEmiratesId);
        dest.writeValue(tenantEmiratesidExpDate);
        dest.writeValue(tenantPassportNumber);
        dest.writeValue(tenantPassportExpDate);
        dest.writeValue(tenantAltMobNo);
        dest.writeValue(tenantLandlineNo);
        dest.writeValue(tenantEmergencyNo);
        dest.writeValue(tenantZip);
        dest.writeValue(companyName);
        dest.writeValue(companyAddr);
        dest.writeValue(propertyCode);
        dest.writeValue(unitCode);
        dest.writeValue(leaseTerm);
        dest.writeValue(leaseType);
        dest.writeValue(leaseStartDate);
        dest.writeValue(leaseEndDate);
        dest.writeValue(paymentTerms);
        dest.writeValue(leaseAmount);
        dest.writeValue(tenantStatus);
        dest.writeValue(dewaPremiseNumber);
    }

    public int describeContents() {
        return  0;
    }

}
