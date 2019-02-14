
package com.residents.dubaiassetmanagement.Helper;

import java.util.List;

import com.residents.dubaiassetmanagement.Model.OccupantDetail;
import com.residents.dubaiassetmanagement.Model.TenantDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TenantDetails1 {

    @SerializedName("tenant_Details")
    @Expose
    private List<com.residents.dubaiassetmanagement.Model.TenantDetail> tenantDetails = null;
    @SerializedName("occupant_Details")
    @Expose
    private List<com.residents.dubaiassetmanagement.Model.OccupantDetail> occupantDetails = null;
    @SerializedName("pet_Details")
    @Expose
    private Object petDetails;

    public List<com.residents.dubaiassetmanagement.Model.TenantDetail> getTenantDetails() {
        return tenantDetails;
    }

    public void setTenantDetails(List<TenantDetail> tenantDetails) {
        this.tenantDetails = tenantDetails;
    }

    public List<com.residents.dubaiassetmanagement.Model.OccupantDetail> getOccupantDetails() {
        return occupantDetails;
    }

    public void setOccupantDetails(List<OccupantDetail> occupantDetails) {
        this.occupantDetails = occupantDetails;
    }

    public Object getPetDetails() {
        return petDetails;
    }

    public void setPetDetails(Object petDetails) {
        this.petDetails = petDetails;
    }

}
