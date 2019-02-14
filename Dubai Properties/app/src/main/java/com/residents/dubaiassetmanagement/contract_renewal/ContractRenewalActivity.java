package com.residents.dubaiassetmanagement.contract_renewal;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.eventbus.Subscribe;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.ServiceRequest.ServicesFragment;
import com.residents.dubaiassetmanagement.contract_renewal.review_proposal.ReviewProposalFragment;
import com.residents.dubaiassetmanagement.contract_renewal.review_proposal.models.ReviewProposal;

import de.greenrobot.event.EventBus;

public class ContractRenewalActivity extends AppCompatActivity implements DrawerLocker{


    private TextView fragmentTitle;
    private BottomNavigationView bottomNavigation;
    private RelativeLayout iv_bell_icon;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    public Toolbar toolbar;
    private LinearLayout ll_renewal_complete,ll_review_proposal,ll_confirm_details,ll_recieve_proposal,ll_schedule_pickup;
    private ImageView iv_reviewProposal,iv_confirm_details;
    private TextView tv_reviewProposal,tv_confirm_details;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_renewal);
        EventBus.getDefault().register(this);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);
        fragmentTitle = (TextView) findViewById(R.id.title_app_bar);
        fragmentTitle.setText("CONTRACT RENEWAL");
        iv_bell_icon = (RelativeLayout) findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ll_renewal_complete = (LinearLayout) findViewById(R.id.ll_renewal_complete);
        ll_review_proposal = (LinearLayout) findViewById(R.id.ll_review_proposal);
        ll_confirm_details = (LinearLayout) findViewById(R.id.ll_confirm_details);
        ll_recieve_proposal = (LinearLayout) findViewById(R.id.ll_recieve_proposal);
        ll_schedule_pickup = (LinearLayout) findViewById(R.id.ll_schedule_pickup);
        iv_reviewProposal = (ImageView) findViewById(R.id.iv_reviewProposal);
        iv_confirm_details= (ImageView) findViewById(R.id.iv_confirm_details);
        tv_reviewProposal = (TextView) findViewById(R.id.tv_reviewProposal);

        ll_review_proposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new DashboardContractRenewal());
            }
        });


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


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


       // loadFragmentStepper(new StepperFragment());
loadFragment(new DashboardContractRenewal());
//startActivity(new Intent(ContractRenewalActivity.this,ContractRenewalDashboardActivity.class));
        ((DrawerLocker)this).setDrawerEnabled(true);
        ((ContractRenewalActivity) this).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();

            }
        });


        FragmentManager fm = this.getFragmentManager();
//
//        ((MainActivity) getActivity()).showbackButton(false);
//
//        ((MainActivity) getActivity()).toolbar_title.setText("Home");




    }

    @Override
    protected void onResume() {
        super.onResume();


        if (getFragmentManager().getBackStackEntryCount() > 1) {
            Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_layout);
            if (f instanceof ReviewProposalFragment) {
                Toast.makeText(this, "NEW", Toast.LENGTH_SHORT).show();
                // Do something
            }
        }
    }
    @Subscribe
    public void onEvent(StepperStatus event) {
        iv_reviewProposal.setImageResource(R.drawable.visited);
    }
    @Subscribe
    public void onEvent(ConfirmDetailsStatus event) {
        iv_reviewProposal.setImageResource(R.drawable.tick_visited);
        iv_confirm_details.setImageResource(R.drawable.visited);
    }

    private void loadFragment(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_layout, fragment);
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
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);

        super.onDestroy();

    }

    @Override
    public void setDrawerOpen(boolean enabled) {
        if (enabled) {
            drawer.openDrawer(GravityCompat.START);
        }
    }
}
