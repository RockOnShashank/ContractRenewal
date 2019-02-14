
package com.residents.dubaiassetmanagement.my_documents.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DPGAttachmentDetailsV implements Parcelable
{

    @SerializedName("ObjectType")
    @Expose
    private String objectType;
    @SerializedName("ObjectReference")
    @Expose
    private String objectReference;
    @SerializedName("Attachment_Type")
    @Expose
    private String attachmentType;
    @SerializedName("File_Name")
    @Expose
    private String fileName;
    @SerializedName("hAttachment")
    @Expose
    private String hAttachment;
    @SerializedName("dtAttachmentCreated")
    @Expose
    private String dtAttachmentCreated;
    @SerializedName("DisplayName")
    @Expose
    private String displayName;
    @SerializedName("TemplateCode")
    @Expose
    private Object templateCode;
    public final static Creator<DPGAttachmentDetailsV> CREATOR = new Creator<DPGAttachmentDetailsV>() {


        @SuppressWarnings({
            "unchecked"
        })
        public DPGAttachmentDetailsV createFromParcel(Parcel in) {
            return new DPGAttachmentDetailsV(in);
        }

        public DPGAttachmentDetailsV[] newArray(int size) {
            return (new DPGAttachmentDetailsV[size]);
        }

    }
    ;

    protected DPGAttachmentDetailsV(Parcel in) {
        this.objectType = ((String) in.readValue((String.class.getClassLoader())));
        this.objectReference = ((String) in.readValue((String.class.getClassLoader())));
        this.attachmentType = ((String) in.readValue((String.class.getClassLoader())));
        this.fileName = ((String) in.readValue((String.class.getClassLoader())));
        this.hAttachment = ((String) in.readValue((String.class.getClassLoader())));
        this.dtAttachmentCreated = ((String) in.readValue((String.class.getClassLoader())));
        this.displayName = ((String) in.readValue((String.class.getClassLoader())));
        this.templateCode = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public DPGAttachmentDetailsV() {
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getObjectReference() {
        return objectReference;
    }

    public void setObjectReference(String objectReference) {
        this.objectReference = objectReference;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHAttachment() {
        return hAttachment;
    }

    public void setHAttachment(String hAttachment) {
        this.hAttachment = hAttachment;
    }

    public String getDtAttachmentCreated() {
        return dtAttachmentCreated;
    }

    public void setDtAttachmentCreated(String dtAttachmentCreated) {
        this.dtAttachmentCreated = dtAttachmentCreated;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Object getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(Object templateCode) {
        this.templateCode = templateCode;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(objectType);
        dest.writeValue(objectReference);
        dest.writeValue(attachmentType);
        dest.writeValue(fileName);
        dest.writeValue(hAttachment);
        dest.writeValue(dtAttachmentCreated);
        dest.writeValue(displayName);
        dest.writeValue(templateCode);
    }

    public int describeContents() {
        return  0;
    }

}
