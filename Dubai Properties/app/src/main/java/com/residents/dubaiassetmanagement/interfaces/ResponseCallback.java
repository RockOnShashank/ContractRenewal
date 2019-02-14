package com.residents.dubaiassetmanagement.interfaces;

public interface ResponseCallback {


    void onSuccess(String response);
    void onSuccessHome(String response);
    void onSuccessNotificationCount(String response);

    void onSuccessSecond(String response);

    void onPostSuccess(String response, String sessionId);

}
