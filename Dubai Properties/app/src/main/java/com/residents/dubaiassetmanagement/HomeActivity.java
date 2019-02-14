package com.residents.dubaiassetmanagement;

import android.Manifest;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.eventbus.Subscribe;
import com.residents.dubaiassetmanagement.Profile.MeFragment;
import com.residents.dubaiassetmanagement.Profile.UpdateNumberOTPFragment;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.CreateNewFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.ScheduleFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.ServicesFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.community_care.create_new.CommunityCareFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.community_care.view_request.ViewDetailCCFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.community_care.view_request.ViewRequestCCFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.create_new.LeasingServicesFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.view_request.ViewDetailLSFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.view_request.ViewRequestLSFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.FeedbackFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.FollowUpFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.HistoryFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.HistoryLSFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewDetailHistoryLC;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestDetailFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.ViewRequest;
import com.residents.dubaiassetmanagement.Utils.ApplicationController;
import com.residents.dubaiassetmanagement.Utils.BottomNavigationViewHelper;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.change_password.ChangePasswordFragment;
import com.residents.dubaiassetmanagement.contact.ContactUsFragment;
import com.residents.dubaiassetmanagement.contract_renewal.StepperStatus;
import com.residents.dubaiassetmanagement.events.BottomNavigationService;
import com.residents.dubaiassetmanagement.events.BottomNavigationStatus;
import com.residents.dubaiassetmanagement.events.CreateNewMSR;
import com.residents.dubaiassetmanagement.firebase.Config;
import com.residents.dubaiassetmanagement.firebase.NotificationUtils;
import com.residents.dubaiassetmanagement.history_cc.HistoryCCFragment;
import com.residents.dubaiassetmanagement.home_screen.HomeDetailViewRequest;
import com.residents.dubaiassetmanagement.home_screen.HomeFeedbackFragment;
import com.residents.dubaiassetmanagement.home_screen.HomeViewDetailCCFragment;
import com.residents.dubaiassetmanagement.home_screen.ScheduleFragmentHome;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallbackRecaptcha;
import com.residents.dubaiassetmanagement.location_util.LocationHelper;
import com.residents.dubaiassetmanagement.my_documents.MyDocumentFragment;
import com.residents.dubaiassetmanagement.notification_list.NotificationList;
import com.residents.dubaiassetmanagement.notifications_dashboard.DashboardNotification;
import com.residents.dubaiassetmanagement.notifications_dashboard.DashboardNotificationsFragment;
import com.residents.dubaiassetmanagement.notifications_dashboard.NotificationAdapter;
import com.residents.dubaiassetmanagement.notifications_dashboard.models.NotificationData;
import com.residents.dubaiassetmanagement.reopen_service.ReopenScheduleFragment;
import com.residents.dubaiassetmanagement.reopen_service.ReopenServiceFragment;
import com.residents.dubaiassetmanagement.terms_of_service.TermsofServiceFragment;
import com.residents.dubaiassetmanagement.touchid.FingeprintActivity;
import com.residents.dubaiassetmanagement.touchid.FingerprintHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.messaging.FirebaseMessaging;
import com.itextpdf.text.pdf.parser.Line;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.zip.Inflater;

import de.greenrobot.event.EventBus;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        ServicesFragment.OnFragmentInteractionListener,
        AmenitiesFragment.OnFragmentInteractionListener,
        MeFragment.OnFragmentInteractionListener,ViewRequestFragment.OnFragmentInteractionListener ,UpdateNumberOTPFragment.OnFragmentInteractionListener,ViewRequestLSFragment.OnFragmentInteractionListener, ViewRequestCCFragment.OnFragmentInteractionListener , View.OnClickListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener , ResponseCallback , FollowUpFragment.FollowedUp, DrawerLocker, ResponseCallbackRecaptcha {


    private static final int PERMISSION_REQUEST_CODE = 200;
    ApplicationPreferences preferences;

    private View view;
    private int isVisible=0;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT = 1;
    private SavePreference mSavePreference;
    LinearLayout my_document,ll_logout;
    public static int count = 0;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private Location mLastLocation;
    double latitude;
    double longitude;
    private String FIREBASE_BASE_URL = "http://dubaiam-integrations-qa.azurewebsites.net/api/Device";
    private String regId = null;
    LocationHelper locationHelper;
    public static int sendDeviceToken;
    private ImageView iv_cross,iv_down;
    ViewGroup root;
    LinearLayout ll_contact_us,ll_settings,ll_child_view,ll_change_password,ll_terms,ll_touchid;
    ActionBarDrawerToggle toggle;
     DrawerLayout drawer;
   public Toolbar toolbar;
    ImageView iv_back_button;
    SwitchCompat switchButton;
    FingerprintManager fingerprintManager;
    private RelativeLayout iv_bell_icon;
    ArrayList<NotificationData> notificationDataList;
    NavigationView navigationView;
    private int z=0;
    private int x=0;
    private int y=0;
    private TextView fragmentTitle;
    private  BottomNavigationView bottomNavigation;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        notificationDataList = new ArrayList<>();
        EventBus.getDefault().register(this);


        // Initializing both Android Keyguard Manager and Fingerprint Manager
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        fingerprintManager = (FingerprintManager) this.getSystemService(FINGERPRINT_SERVICE);
        fragmentTitle = (TextView) findViewById(R.id.title_app_bar);

        preferences =  ApplicationPreferences.getInstance(this);
        mSavePreference = SavePreference.getInstance(this);
        my_document = (LinearLayout) findViewById(R.id.my_documents);
        ll_logout = (LinearLayout) findViewById(R.id.ll_logout);
        ll_contact_us = (LinearLayout) findViewById(R.id.ll_contact_us);
        ll_touchid = (LinearLayout) findViewById(R.id.ll_touchid);
        ll_settings = (LinearLayout) findViewById(R.id.ll_settings);
        ll_terms = (LinearLayout) findViewById(R.id.ll_terms);
        ll_child_view = (LinearLayout) findViewById(R.id.ll_child_view);
        ll_change_password = (LinearLayout) findViewById(R.id.ll_change_password);
        iv_back_button = (ImageView) findViewById(R.id.iv_back_button);
        iv_bell_icon = (RelativeLayout) findViewById(R.id.iv_bell_icon);
        iv_down = (ImageView) findViewById(R.id.iv_down);
        iv_cross = (ImageView) findViewById(R.id.iv_cross);
        my_document.setOnClickListener(this);
        ll_contact_us.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
        ll_settings.setOnClickListener(this);
        ll_child_view.setOnClickListener(this);
        ll_change_password.setOnClickListener(this);
        ll_terms.setOnClickListener(this);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString("MyObject1", "");

        prefsEditor.putString("CC_List", "");
        prefsEditor.putString("LS_List", "");

        prefsEditor.commit();

         toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        //Custom Hamburger icon
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_hamburger_icon);

        //Hamburger on click
      toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        new RequestService(this, HomeActivity.this, HomeActivity.this).setArgumentsNotification("Tenant/" + mSavePreference.getString(IpreferenceKey.TCODE) + "/Notifications");

        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//Bottom

        //Bottom Navigation
         bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation_bottom);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigation);
        root = (ViewGroup) getWindow().getDecorView().getRootView();

        bottomNavigation.setItemIconTintList(null);



        //Load Home fragment by default
        loadFragment(new HomeFragment());
        // Get runtime permissions if build version >= Android M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermission()) {
                ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

            }
        }
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");
                    String messages = intent.getStringExtra("message");


//
//                    txtMessage.setText(message);

                    //  boolean badge= ShortcutBadger.applyCount(context, badgecount); //for 1.1.4+
                    //  boolean badgesuppoprted=  ShortcutBadger.isBadgeCounterSupported(getApplicationContext());//for 1.1.3
                }
            }
        };
       // new RequestService(this, HomeActivity.this).setArgumentsSecond("Tenant/"+mSavePreference.getString(IpreferenceKey.TCODE)+"/Notifications/Community");

        displayFirebaseRegId();

        locationHelper = new LocationHelper(this);
       // locationHelper.checkpermission();
        // check availability of play services
        if (locationHelper.checkPlayServices()) {

            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient();
        }


        if (sendDeviceToken==1) {
          //  startActivity(new Intent(HomeActivity.this, DashboardNotification.class));


            // Tenant/967000/Notifications/ProfileLite
           // new RequestService(HomeActivity.this, HomeActivity.this).postRequestFirebase("Device", jsonObject);
            new RequestService(this, HomeActivity.this).setArgumentsSecond("Tenant/"+mSavePreference.getString(IpreferenceKey.TCODE)+"/Notifications/ProfileLite");



            SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
            regId = pref.getString("regId", null);

            JSONObject jsonObject = new JSONObject();

            try {

                jsonObject.put("TenantCode", mSavePreference.getString(IpreferenceKey.TCODE));
                jsonObject.put("DeviceToken", regId);
                jsonObject.put("IsiOS", "false");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            new RequestService(HomeActivity.this, HomeActivity.this).postRequestFirebase("Device", jsonObject);
        }
        sendDeviceToken=0;
        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        switchButton=(SwitchCompat)findViewById(R.id.switchButton);
        switchButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return event.getActionMasked() == MotionEvent.ACTION_MOVE;
            }
        });
        switchButton.setOnClickListener(this);

        if (mSavePreference.getString(IpreferenceKey.TOUCHID)!=null) {
            if (mSavePreference.getString(IpreferenceKey.TOUCHID).equalsIgnoreCase("enable")) {
                switchButton.setChecked(true);
            } else {
                switchButton.setChecked(false);

            }
        }


        if (fingerprintManager==null){
           ll_touchid.setVisibility(View.GONE);
        }else {
            // Check whether the device has a Fingerprint sensor.
            if (!fingerprintManager.isHardwareDetected()) {
                ll_touchid.setVisibility(View.GONE);
                /**
                 * An error message will be displayed if the device does not contain the fingerprint hardware.
                 * However if you plan to implement a default authentication method,
                 * you can redirect the user to a default authentication activity from here.
                 * Example:
                 * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
                 * startActivity(intent);
                 */
            } else {
                ll_touchid.setVisibility(View.VISIBLE);


            }
        }

      //  loadFragment(new DashboardNotificationsFragment());
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_frame_layout);
        if (fragment instanceof HomeFragment || fragment instanceof ServicesFragment || fragment instanceof MeFragment){
            iv_bell_icon.setVisibility(View.VISIBLE);
        }else {
            iv_bell_icon.setVisibility(View.INVISIBLE);
        }
      //  new RequestService(this, HomeActivity.this).setArgumentsNotificationCount("Tenant/" + mSavePreference.getString(IpreferenceKey.TCODE) + "/Notifications");
        SharedPreferences appSharedPrefs1 = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor1 = appSharedPrefs1.edit();
        prefsEditor1.putString("Home_List", "");


        prefsEditor1.commit();

    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e("HA", "Firebaseregid: " + regId);

    }

    /* @Override
     public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
         switch (requestCode) {
             case PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT: {
                 if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                     mSavePreference.putBoolean(IpreferenceKey.IS_PERMISSIONS_GRANTED,false);
               //   Toast.makeText(this, "Permissions Granted", Toast.LENGTH_LONG).show();
                 } else {
                    mSavePreference.putBoolean(IpreferenceKey.IS_PERMISSIONS_GRANTED,true);
                //    Toast.makeText(this, "Insufficient Permission", Toast.LENGTH_LONG).show();

                 }
             }
         }
     }*/
    private void loadFragment(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            Fragment fragments = getSupportFragmentManager().findFragmentById(R.id.home_frame_layout);

            switch (menuItem.getItemId()) {
                case R.id.home_bottom_nav:
                    if (!(fragments instanceof HomeFragment)) {

                        Fragment fragment0 = new HomeFragment();
                        loadFragment(fragment0);

                        return true;
                    }else {
                        return false;
                    }
                case R.id.services_bottom_nav:

                    if (!(fragments instanceof ServicesFragment)) {

                        Fragment fragment1 = new ServicesFragment();
                        loadFragment(fragment1);

                        return true;
                    }else {
                        return false;
                    }
                /*case R.id.amenities_bottom_nav:
                    fragment = new AmenitiesFragment();
                    loadFragment(fragment);
                    return true;*/
                case R.id.me_bottom_nav:
                    if (!(fragments instanceof MeFragment)  ) {

                        Fragment fragment2 = new MeFragment();
                        loadFragment(fragment2);
                        return true;
                    }else {
                        return false;
                    }
            }
x=0;
            y=0;
            z=0;
            return false;
        }
    };


    @Override
    public void onBackPressed() {




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_frame_layout);

            if ( fragment instanceof ScheduleFragment|| fragment instanceof CreateNewFragment ||
                     fragment instanceof CommunityCareFragment  || fragment instanceof HistoryCCFragment ||
                    fragment instanceof LeasingServicesFragment
                      || fragment instanceof ViewRequestCCFragment ||
                    fragment instanceof ViewRequestLSFragment  || fragment instanceof ViewDetailHistoryLC || fragment instanceof HistoryLSFragment ||
                    fragment instanceof FeedbackFragment ||fragment instanceof ScheduleFragmentHome || fragment instanceof ViewDetailCCFragment || fragment instanceof FollowUpFragment ||

                       fragment instanceof ViewDetailLSFragment || fragment instanceof ReopenServiceFragment  ||  fragment instanceof ReopenScheduleFragment) {
                super.onBackPressed();
                clearDim(root);

            }
            else if ( fragment instanceof ViewRequestFragment ) {
                loadFragment(new ServicesFragment());
            }
            else if ( fragment instanceof ViewRequestDetailFragment ) {
                loadFragment(new ViewRequestFragment());
            }
            else if ( fragment instanceof HistoryFragment ) {
                loadFragment(new ViewRequestFragment());
            }
            else if ( fragment instanceof MyDocumentFragment ) {

                loadFragment(new HomeFragment());
            }
            else if ( fragment instanceof HomeFeedbackFragment) {

                loadFragment(new HomeFragment());
            }
            else if ( fragment instanceof ChangePasswordFragment ) {
                loadFragment(new HomeFragment());
       }
            else if ( fragment instanceof HomeDetailViewRequest ) {
                loadFragment(new HomeFragment());
            }
            else if ( fragment instanceof ContactUsFragment ) {
                loadFragment(new HomeFragment());
            }
            else if ( fragment instanceof TermsofServiceFragment ) {
                loadFragment(new HomeFragment());
            }
            else if ( fragment instanceof UpdateNumberOTPFragment ) {
                loadFragment(new MeFragment());
            }
            else if ( fragment instanceof NotificationList ) {
                loadFragment(new HomeFragment());
            }
            else if ( fragment instanceof HomeViewDetailCCFragment) {
                loadFragment(new HomeFragment());
            }
            else if ( fragment instanceof ViewRequestLSFragment) {
                loadFragment(new ViewRequestLSFragment());
            }
            else if ( fragment instanceof ViewDetailHistoryLC) {
                loadFragment(new HistoryLSFragment());
            }
            else if ( fragment instanceof HistoryLSFragment) {
                loadFragment(new ViewRequestLSFragment());
            }
            else if ( fragment instanceof ServicesFragment || fragment instanceof MeFragment) {
               finishAffinity();
            }

            else
            {
                finishAffinity();
            }
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.my_documents:
                Toast.makeText(this, "hey", Toast.LENGTH_LONG).show();
                break;
        }


       /* if (id == R.id.my_documents) {
            Toast.makeText(this,"hey",Toast.LENGTH_LONG).show();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted || cameraAccepted) {
                        mSavePreference.putString(IpreferenceKey.IS_PERMISSIONS_GRANTED, "true");
                    } //Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    else {
                        mSavePreference.putString(IpreferenceKey.IS_PERMISSIONS_GRANTED, "false");

// Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                //  showMessageOKCancel("You need to allow access to both the permissions",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE},
                                                    PERMISSION_REQUEST_CODE);
                                        }
                                    }
                                };
                                return;
                            }
                        }
                    }
                }

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_documents:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                MyDocumentFragment fragment = new MyDocumentFragment();
                loadFragment(fragment);
                break;
            case R.id.ll_settings:
if (isVisible==0){
iv_down.setRotation(90);
    ll_child_view.setVisibility(View.VISIBLE);
    isVisible=1;

}else if (isVisible==1){
    iv_down.setRotation(270);

    isVisible =0;
    ll_child_view.setVisibility(View.GONE);
}

                break;
            case R.id.ll_change_password:
                DrawerLayout drawer_ll = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer_ll.closeDrawer(GravityCompat.START);
                ChangePasswordFragment changePasswordFragment = new ChangePasswordFragment();
                loadFragment(changePasswordFragment);
                break;

            case R.id.ll_logout:
                DrawerLayout drawer1 = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer1.closeDrawer(GravityCompat.START);
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle( "" )
                        .setMessage("Are you sure you want to logout?")
     .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
     public void onClick(DialogInterface dialoginterface, int i) {
         dialoginterface.cancel();
         }})
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                new RequestService(HomeActivity.this, HomeActivity.this).setArgumentsLogout("Profile/Logout?profileId="+mSavePreference.getString(IpreferenceKey.TCODE)+"&sessionId="+preferences.getSessionId());
                            }
                        }).show();


            break;

            case R.id.ll_contact_us:

                loadFragment(new ContactUsFragment());
                DrawerLayout drawer2 = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer2.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_terms:

                loadFragment(new TermsofServiceFragment());
                DrawerLayout drawer3 = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer3.closeDrawer(GravityCompat.START);
                break;


            case R.id.switchButton:
                mSavePreference.putString(IpreferenceKey.TOUCHID,"disable");

if (switchButton.isChecked()){
    mSavePreference.putString(IpreferenceKey.TOUCHID,"enable");
    Toast.makeText(HomeActivity.this, "Touch Id Enabled", Toast.LENGTH_LONG).show();
}else {
    mSavePreference.putString(IpreferenceKey.TOUCHID,"disable");
    Toast.makeText(HomeActivity.this, "Touch Id Disabled", Toast.LENGTH_LONG).show();

}
                /*loadFragment(new TermsofServiceFragment());
                DrawerLayout drawer3 = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer3.closeDrawer(GravityCompat.START);*/
                break;
        }

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);

        super.onPause();
    }

    private BroadcastReceiver timerReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // new Logout().execute();
            //mSavePreference.putBoolean(IpreferenceKey.ISFIRSTTIME, false);
            finish();
        }

    };

    private void registerReceiver() {
        IntentFilter filterregisterReceiver = new IntentFilter();
        filterregisterReceiver.addAction("TIMERINTENT");
        registerReceiver(timerReceiver, filterregisterReceiver);
    }

    @Override
    protected void onResume() {


        getCurrentVersion();

        registerReceiver();

        super.onResume();
        locationHelper.checkPlayServices();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("Connection failed:", " ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        mLastLocation = locationHelper.getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        locationHelper.connectApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onSuccess(String response) {
        String res = response;
        try {
            JSONObject jsonObject = new JSONObject(res);

            String status = jsonObject.getString("Status");

            if (status.equalsIgnoreCase("Success")){
                preferences.clearSavedCredentials();
                mSavePreference.clear();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                finishAffinity();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccessHome(String response) {

    }

    @Override
    public void onSuccessNotificationCount(String response) {


    }

    @Override
    public void onSuccessSecond(String response) {
        String res = response;
       // mSavePreference.putString(IpreferenceKey.COMMUNITY_NAME,response.replaceAll("^\"|\"$", ""));
        try {
            JSONObject jsonObject = new JSONObject(res);
            mSavePreference.putString(IpreferenceKey.COMMUNITY_NAME,jsonObject.getString("CommunityName"));
            mSavePreference.putString(IpreferenceKey.PHONE_NUMBER, jsonObject.getString("Mobile"));



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostSuccess(String response, String sessionId) {
      //  Toast.makeText(HomeActivity.this, "Success", Toast.LENGTH_LONG).show();

    }

    @Override
    public void message(String msg) {

    }
    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }

    private void loadFragments(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void setDrawerEnabled(boolean enabled) {

        if(enabled){
            toolbar.setNavigationIcon(R.drawable.open_arrow);

        }else{
            toolbar.setNavigationIcon(R.drawable.ic_hamburger_icon);

        }

    }

    @Override
    public void setDrawerOpen(boolean enabled) {
        if (enabled) {
            drawer.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onSuccessRecaptcha(String response) {

    }
    @Subscribe
    public void onEvent(StepperStatus event) {
loadFragment(new NotificationList());


    }
    @Subscribe
    public void onEvent(BottomNavigationStatus event) {

fragmentTitle.setText("HOME");
            bottomNavigation.getMenu().findItem(R.id.home_bottom_nav).setChecked(true);

    }
    @Subscribe
    public void onEvent(CreateNewMSR event) {

        Fragment fragments = getSupportFragmentManager().findFragmentById(R.id.home_frame_layout);
if (fragments instanceof ScheduleFragment){
    EventBus.getDefault().post(new BottomNavigationStatus());

    getSupportFragmentManager().popBackStack();
}else if (fragments instanceof CreateNewFragment){
    loadFragment(new ServicesFragment());
}

    }
    @Subscribe
    public void onEvent(BottomNavigationService event) {


        bottomNavigation.getMenu().findItem(R.id.services_bottom_nav).setChecked(true);

    }
    @Override
    public void onSuccessJWT(String response) {


int i;
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (i = 0; i <= jsonArray.length()-1; i++) {
                NotificationData notificationData = new NotificationData();


                JSONObject notification = jsonArray.getJSONObject(i).getJSONObject("Notification");
                notificationData.title = notification.getString("Title");
                notificationData.description = notification.getString("Description");
                notificationData.photo_url = notification.getString("Icon");
                notificationData.isSeen = jsonArray.getJSONObject(i).getString("IsSeen");

                if (notificationData.isSeen.equalsIgnoreCase("false")) {
                    notificationDataList.add(notificationData);
                }

            }
            if (notificationDataList.size()==0){

            }
            else
                {
                startActivity(new Intent(HomeActivity.this, DashboardNotification.class));
            }

        }catch (Exception e){

        }
    }
    private void getCurrentVersion() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);
// System.out.println("package:"+pInfo.packageName);

        } catch (PackageManager.NameNotFoundException e1) {
// TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String currentVersion = pInfo.versionName;
        System.out.println("Cversion:"+currentVersion);


        new GetLatestVersion(currentVersion, HomeActivity.this).execute();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}


