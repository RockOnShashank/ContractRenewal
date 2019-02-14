package com.residents.dubaiassetmanagement;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import com.residents.dubaiassetmanagement.Adapters.OnboardingAdapter;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.touchid.FingeprintActivity;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager mPager;
    private int[] layouts = {R.layout.onboarding_1};
    private OnboardingAdapter mpagerAdapter;
    private LinearLayout Dots_Layout;
    private ImageView[] dots;
    private Button buttonSignUp, buttonLogIn;
    private SavePreference mSavePreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        mSavePreference = SavePreference.getInstance(this);
        if (mSavePreference.getString(IpreferenceKey.IS_LOGIN)!=null) {
            if (mSavePreference.getString(IpreferenceKey.IS_LOGIN).equalsIgnoreCase("true")) {
if (mSavePreference.getString(IpreferenceKey.TOUCHID) != null) {
    if (mSavePreference.getString(IpreferenceKey.TOUCHID).equalsIgnoreCase("enable")) {
        startActivity(new Intent(OnboardingActivity.this, FingeprintActivity.class));

    } else {
        startActivity(new Intent(OnboardingActivity.this, HomeActivity.class));
    }
}else {
    startActivity(new Intent(OnboardingActivity.this, HomeActivity.class));
}

            } else {
                buttonLogIn = (Button) findViewById(R.id.button_log_in);
                buttonSignUp = (Button) findViewById(R.id.button_sign_up);

                //slider using view pager
                setContentView(R.layout.activity_onboarding);
                mPager = (ViewPager) findViewById(R.id.viewPager);
                mpagerAdapter = new OnboardingAdapter(layouts, this);
                mPager.setAdapter(mpagerAdapter);
                Dots_Layout = (LinearLayout) findViewById(R.id.dotslayout);


                createDots(0);
                mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {
                    }

                    @Override
                    public void onPageSelected(int i) {
                        createDots(i);
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {
                    }
                });
                //Hockey App Check Updates
                checkForUpdates();

            }
        }else{

            buttonLogIn = (Button) findViewById(R.id.button_log_in);
            buttonSignUp = (Button) findViewById(R.id.button_sign_up);

            //slider using view pager
            setContentView(R.layout.activity_onboarding);
            mPager = (ViewPager) findViewById(R.id.viewPager);
            mpagerAdapter = new OnboardingAdapter(layouts, this);
            mPager.setAdapter(mpagerAdapter);
            Dots_Layout = (LinearLayout) findViewById(R.id.dotslayout);


            createDots(0);
            mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                }

                @Override
                public void onPageSelected(int i) {
                    createDots(i);
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                }
            });
            //Hockey App Check Updates
            checkForUpdates();

        }

    }
    //Hockey App Fucnctions

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }


    //creating the slider dots function
    private void createDots(int current_position) {

        if (Dots_Layout != null)
            Dots_Layout.removeAllViews();

        dots = new ImageView[layouts.length];

        for (int i = 0; i < layouts.length; i++) {

            dots[i] = new ImageView(this);

            if (i == current_position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.onboarding_active_dots));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.onboarding_inactive_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);

           // Dots_Layout.addView(dots[i], params);
        }

    }

    //Navigate to LoginActivity
    public void LoginActivity(View view) {

        startActivity(new Intent(OnboardingActivity.this, LoginActivity.class));
        finish();
    }

    public void SignUpActivity(View view) {
        startActivity(new Intent(OnboardingActivity.this, RegisterActivity.class));
        finish();
    }
}
