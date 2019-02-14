package com.residents.dubaiassetmanagement.Utils;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class ApplicationController extends Application {


    /**
     * Log or request TAG
     */
    private static final String TAG = "CONTROLLER_VOLLEY";
    private static Context mContext;

    /**
     * Global request queue for Volley
     */

    /**
     * a singleton instance of the application class for easy access in other
     * places
     */
    private static ApplicationController sInstance;
    private RequestQueue mRequestQueue;

    /**
     * set max retries
     */
    private static int MAX_RETRIES = 3;

    TelephonyManager mTelManager = null;

    private ConnectivityManager mConManager = null;

    /**
     * Holds the device info
     */
    Map<String, String> mDeviceInfo = null;

    public static Context getContext() {
        //  return instance.getApplicationContext();
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        // initialize the singleton
        sInstance = this;
        mContext = getApplicationContext();

    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized ApplicationController getInstance() {
        return sInstance;
    }



    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */


    /**
     * Adds the specified request to the global queue, if tag is specified then
     * it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */


    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */



    /**
     * Returns connectivity manager instance
     *
     * @return
     */
    private ConnectivityManager getConnectivityManager() {

        if (mConManager == null) {
            mConManager = (ConnectivityManager) getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        return mConManager;
    }


    /**
     * Returns a map containing the devices information. Key are the constants
     * defined.
     *
     * @return Map holding device info
     *//*
    public Map<String, String> getDeviceInfo() {

		if (mDeviceInfo == null) {
			mDeviceInfo = new HashMap<String, String>();
			mDeviceInfo.put(DEVICE_IMEI,
					(getTelephoyManager().getDeviceId() == null ? ""
							: getTelephoyManager().getDeviceId()));
			mDeviceInfo.put(DEVICE_MAKE, (Build.MANUFACTURER == null ? ""
					: Build.MANUFACTURER));
			mDeviceInfo.put(DEVICE_MODEL, (Build.MODEL == null ? ""
					: Build.MODEL));
			mDeviceInfo.put(SIM_CARRIER, (getTelephoyManager()
					.getSimOperatorName() == null ? "" : getTelephoyManager()
					.getSimOperatorName()));
			mDeviceInfo.put(MOB_NO,
					(getTelephoyManager().getLine1Number() == null ? ""
							: getTelephoyManager().getLine1Number()));
			mDeviceInfo.put(SUBSCRIBER_ID, (getTelephoyManager()
					.getSubscriberId() == null ? "" : getTelephoyManager()
					.getSubscriberId()));
		}
		return mDeviceInfo;
	}*/

    /**
     * Checks whether the network is available or not.
     *
     * @return
     */
    public boolean isNetworkAvailable() {

        NetworkInfo[] info = getConnectivityManager().getAllNetworkInfo();
        if (info != null) {
            for (NetworkInfo infoVal : info) {
                if (infoVal.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean isRealDevice() {
        SensorManager manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tm.getNetworkOperatorName();
        if (!manager.getSensorList(Sensor.TYPE_ALL).isEmpty() && "Android".equals(networkOperator)) {
            // running on a device
            System.out.println(" Found Emulator");
            return false;
        } else {

            System.out.println("Found Device");
            return true;
        }

    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    private RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
            return mRequestQueue;
        }

        return mRequestQueue;
    }


    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        req.setRetryPolicy(new DefaultRetryPolicy(60000, MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }



}

