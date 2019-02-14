package com.residents.dubaiassetmanagement.SavePreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SavePreference {
    private static final String PREFERANCE = "dubaiPref";
    private SharedPreferences sharedPreferences;
    private Context context;
    private static SavePreference mSavePreference = null;

    private SavePreference(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("dubaiPref", 0);
    }

    public static SavePreference getInstance(Context context) {
        if (mSavePreference == null) {
            mSavePreference = new SavePreference(context);
            return mSavePreference;
        } else {
            return mSavePreference;
        }
    }

    public void putBoolean(String key, boolean value) {
        Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key) {
        boolean pvalue = this.sharedPreferences.getBoolean(key, false);
        return pvalue;
    }

    public void putFloat(String key, float value) {
        Editor editor = this.sharedPreferences.edit();
        editor.putFloat(key, value).commit();
    }

    public float getFloat(String key) {
        float pvalue = this.sharedPreferences.getFloat(key, 0.0F);
        return pvalue;
    }

    public void putInt(String key, int value) {
        Editor editor = this.sharedPreferences.edit();
        editor.putInt(key, value).commit();
    }

    public void putString(String key, String value) {
        Editor editor = this.sharedPreferences.edit();
        editor.putString(key, value).commit();
    }

    public int getInt(String key) {
        int pvalue = this.sharedPreferences.getInt(key, 0);
        return pvalue;
    }

    public String getString(String key) {
        String pvalue = this.sharedPreferences.getString(key, (String)null);
        return pvalue;
    }

    public void clear() {
        Editor editor = this.sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public int getIntHeader(String key) {
        int pvalue = this.sharedPreferences.getInt(key, -1);
        return pvalue;
    }
}
