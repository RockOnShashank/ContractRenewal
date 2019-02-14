
package com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateRequest {

    @SerializedName("Categories")
    @Expose
    private List<Category> categories = null;

    @SerializedName("Status")
    @Expose
    private Object status;


    @SerializedName("Error_Message")
    @Expose
    private Object errorMessage;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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
