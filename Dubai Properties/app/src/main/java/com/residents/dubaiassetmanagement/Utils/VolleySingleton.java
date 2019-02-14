package com.residents.dubaiassetmanagement.Utils;

import android.content.Context;

import com.residents.dubaiassetmanagement.Model.TenantDetails;

public class VolleySingleton {
    private static VolleySingleton mVolleySingleton;
    private Context mContext;

    public static VolleySingleton getmVolleySingleton() {
        return mVolleySingleton;
    }

    public static void setmVolleySingleton(VolleySingleton mVolleySingleton) {
        VolleySingleton.mVolleySingleton = mVolleySingleton;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public TenantDetails getTenantDetails() {
        return tenantDetails;
    }

    public void setTenantDetails(TenantDetails tenantDetails) {
        this.tenantDetails = tenantDetails;
    }

    TenantDetails tenantDetails;
    private VolleySingleton(Context context) {
        this.mContext = context;
    }

    public static VolleySingleton getInstance() {
        if (mVolleySingleton == null) {
            mVolleySingleton = new VolleySingleton(ApplicationController.getInstance());
        }
        return mVolleySingleton;

    }


}
