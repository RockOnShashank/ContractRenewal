package com.residents.dubaiassetmanagement;

import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationPreferences {
    public static final String PREFERENCES_FILE = "settings";

    private static final String IS_CREDENTIALS_SAVED_KEY = "is_creds_saved";
    private static final String PASSWORD_KEY = "password";
    private static final String USERNAME_KEY = "username";


    private static final String USERNAME_JWT= "username_jwt";
    private static final String PASSWORD_JWT = "password_jwt";
    private static final String ACCESS_TOKEN = "access_token";

    private static ApplicationPreferences instance;

    private SharedPreferences preferences;
    private PasswordStorageHelper passwordStorage;

    private ApplicationPreferences(Context context) {
        preferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        passwordStorage = new PasswordStorageHelper(context);
    }

    public static ApplicationPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new ApplicationPreferences(context);
        }

        return instance;
    }

    public boolean isCredentialsSaved() {
        return preferences.getBoolean(IS_CREDENTIALS_SAVED_KEY, false);
    }

    public void setCredentialsSaveEnabled(boolean isEnabled) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_CREDENTIALS_SAVED_KEY, isEnabled);
        editor.commit();
    }

    public void savePassword(String password) {
        passwordStorage.setData(PASSWORD_KEY, password.getBytes());
    }

    public String getLastStoredPassword() {
        return new String(passwordStorage.getData(PASSWORD_KEY));
    }

    public void clearSavedCredentials() {
        setCredentialsSaveEnabled(false);
        passwordStorage.remove(PASSWORD_KEY);
    }

    public void setSessionId(String username) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.commit();
    }

    public String getSessionId() {
        return preferences.getString(USERNAME_KEY, "");
    }

    //jwt

    public void setAccessToken(String accessToken) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.commit();
    }

    public String getAccessToken() {
        return preferences.getString(ACCESS_TOKEN, "");
    }

    public void setUsernameJwt(String username) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERNAME_JWT, username);
        editor.commit();
    }

    public String getUsernameJwt() {
        return preferences.getString(USERNAME_JWT, "");
    }

    public void setPassowordJwt(String username) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PASSWORD_JWT, username);
        editor.commit();
    }

    public String getPasswordJwt() {
        return preferences.getString(PASSWORD_JWT, "");
    }


}
