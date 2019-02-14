package com.residents.dubaiassetmanagement.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateTenantProfiles {

    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Error_Message")
    @Expose
    private Object errorMessage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

}