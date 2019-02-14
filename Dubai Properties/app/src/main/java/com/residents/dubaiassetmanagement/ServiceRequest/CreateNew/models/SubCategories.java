
package com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategories {

    @SerializedName("SubCategories")
    @Expose
    private List<SubCategory> subCategories = null;
    @SerializedName("Status")
    @Expose
    private Object status;
    @SerializedName("Error_Message")
    @Expose
    private Object errorMessage;

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

}
