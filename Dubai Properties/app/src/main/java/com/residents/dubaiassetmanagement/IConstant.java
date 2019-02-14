package com.residents.dubaiassetmanagement;

import android.content.Context;

import com.residents.dubaiassetmanagement.Utils.ApplicationController;
import com.residents.dubaiassetmanagement.firebase.Config;

public class  IConstant {

    private Context con;
   static ApplicationPreferences preferences = ApplicationPreferences.getInstance(ApplicationController.getContext());

    //QA-apigateway ---   https://dubaiam-apigateway-qa.azure-api.net/api/    ///  3258a73a9b3a4449af9328d933af22fd
    //QA---    http://dubaiam-integrations-qa.azurewebsites.net/api/
    //dev---   https://dubaiam-apigateway-qa.azure-api.net/sit1/v1.0/api/     ///  2facc5501bf945748d0b4afc519a79f5   //new 7483f6fe052947f8a793abf55c404512

    // uat---   https://dubaiam-uat.azure-api.net/api/                 ///     1230a368c70348dd983d686dbf0e5ad8
  // public static String  BASE_URL = "https://dubaiam-apigateway-qa.azure-api.net/sit1/v1.0/api/";
    public static String  BASE_URL = BuildConfig.BaseUrl;

    public static String KEY_STORE = BuildConfig.KEY_STORE;
    public static String KEY = preferences.getLastStoredPassword();


    // ___https://deloittecustomerportalscal-333870-cd.azurewebsites.net/InstagramFeed?communityCode=AKG -----instafeed api
//sitekey-  6LdHW38UAAAAAJPFbzkTZK3qYkv-BzdBwTvQXEyD
//server-key-  6LdHW38UAAAAAC7i2MeKQyU6j4kVF8y8ECL5Ztxt


}
