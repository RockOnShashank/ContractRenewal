
package com.residents.dubaiassetmanagement.notifications_dashboard.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification implements Parcelable
{

    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Caption")
    @Expose
    private Object caption;
    @SerializedName("Icon")
    @Expose
    private String icon;
    @SerializedName("NotificationTime")
    @Expose
    private String notificationTime;
    @SerializedName("CssClass")
    @Expose
    private String cssClass;
    @SerializedName("Actions")
    @Expose
    private Object actions;
    @SerializedName("ActionNames")
    @Expose
    private Object actionNames;
    @SerializedName("ChannelId")
    @Expose
    private String channelId;
    public final static Creator<Notification> CREATOR = new Creator<Notification>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        public Notification[] newArray(int size) {
            return (new Notification[size]);
        }

    }
    ;

    protected Notification(Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.caption = ((Object) in.readValue((Object.class.getClassLoader())));
        this.icon = ((String) in.readValue((String.class.getClassLoader())));
        this.notificationTime = ((String) in.readValue((String.class.getClassLoader())));
        this.cssClass = ((String) in.readValue((String.class.getClassLoader())));
        this.actions = ((Object) in.readValue((Object.class.getClassLoader())));
        this.actionNames = ((Object) in.readValue((Object.class.getClassLoader())));
        this.channelId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Notification() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getCaption() {
        return caption;
    }

    public void setCaption(Object caption) {
        this.caption = caption;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        this.notificationTime = notificationTime;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public Object getActions() {
        return actions;
    }

    public void setActions(Object actions) {
        this.actions = actions;
    }

    public Object getActionNames() {
        return actionNames;
    }

    public void setActionNames(Object actionNames) {
        this.actionNames = actionNames;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(description);
        dest.writeValue(caption);
        dest.writeValue(icon);
        dest.writeValue(notificationTime);
        dest.writeValue(cssClass);
        dest.writeValue(actions);
        dest.writeValue(actionNames);
        dest.writeValue(channelId);
    }

    public int describeContents() {
        return  0;
    }

}
