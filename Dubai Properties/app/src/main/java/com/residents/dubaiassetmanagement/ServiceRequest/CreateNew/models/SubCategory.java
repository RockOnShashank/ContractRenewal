
package com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategory {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("SubCategoryName")
    @Expose
    private String subCategoryName;
    @SerializedName("SubCategoryDesc")
    @Expose
    private String subCategoryDesc;
    @SerializedName("TemplateCode")
    @Expose
    private String templateCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategoryDesc() {
        return subCategoryDesc;
    }

    public void setSubCategoryDesc(String subCategoryDesc) {
        this.subCategoryDesc = subCategoryDesc;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

}
