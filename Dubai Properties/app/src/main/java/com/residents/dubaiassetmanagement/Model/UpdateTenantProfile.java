package com.residents.dubaiassetmanagement.Model;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateTenantProfile {

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
