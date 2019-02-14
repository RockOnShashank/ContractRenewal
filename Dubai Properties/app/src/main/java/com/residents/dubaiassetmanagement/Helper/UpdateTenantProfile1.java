package com.residents.dubaiassetmanagement.Helper;



import java.util.List;

import com.residents.dubaiassetmanagement.Model.UpdateTenantProfiles;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateTenantProfile1 {

    @SerializedName("UpdateTenantProfile")
    @Expose
    private List<UpdateTenantProfiles> updateTenantProfile = null;

    public List<UpdateTenantProfiles> getUpdateTenantProfile() {
        return updateTenantProfile;
    }

    public void setUpdateTenantProfile(List<UpdateTenantProfiles> updateTenantProfile) {
        this.updateTenantProfile = updateTenantProfile;
    }

}
