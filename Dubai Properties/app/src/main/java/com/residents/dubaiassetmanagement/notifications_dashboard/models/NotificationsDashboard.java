
package com.residents.dubaiassetmanagement.notifications_dashboard.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationsDashboard implements Parcelable
{

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("ProfileId")
    @Expose
    private String profileId;
    @SerializedName("Notification")
    @Expose
    private Notification notification;
    @SerializedName("TenantCode")
    @Expose
    private String tenantCode;
    @SerializedName("IsSeen")
    @Expose
    private Boolean isSeen;
    public final static Creator<NotificationsDashboard> CREATOR = new Creator<NotificationsDashboard>() {


        @SuppressWarnings({
            "unchecked"
        })
        public NotificationsDashboard createFromParcel(Parcel in) {
            return new NotificationsDashboard(in);
        }

        public NotificationsDashboard[] newArray(int size) {
            return (new NotificationsDashboard[size]);
        }

    }
    ;

    protected NotificationsDashboard(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.profileId = ((String) in.readValue((String.class.getClassLoader())));
        this.notification = ((Notification) in.readValue((Notification.class.getClassLoader())));
        this.tenantCode = ((String) in.readValue((String.class.getClassLoader())));
        this.isSeen = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public NotificationsDashboard() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public Boolean getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(Boolean isSeen) {
        this.isSeen = isSeen;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(profileId);
        dest.writeValue(notification);
        dest.writeValue(tenantCode);
        dest.writeValue(isSeen);
    }

    public int describeContents() {
        return  0;
    }

}
