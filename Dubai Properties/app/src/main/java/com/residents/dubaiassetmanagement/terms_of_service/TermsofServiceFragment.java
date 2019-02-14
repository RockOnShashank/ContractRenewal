package com.residents.dubaiassetmanagement.terms_of_service;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.HomeFragment;
import com.residents.dubaiassetmanagement.R;

public class TermsofServiceFragment extends Fragment {

    WebView wv_terms;

private View view;
private TextView fragmentTitle;
private RelativeLayout iv_bell_icon;
private BottomNavigationView bottomNavigation;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.termsofservice, container, false);
        //Set Fragment Title
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("TERMS AND CONDITIONS");

        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);

        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);
        wv_terms = (WebView) view.findViewById(R.id.wv_terms);
        wv_terms.loadUrl("https://residents.dubaiam.ae/terms-and-conditions-mbl");

     //   wv_terms.loadUrl("https://deloittecustomerportalscal-333870-cd.azurewebsites.net/terms-and-conditions-mbl");
        WebSettings webSettings = wv_terms.getSettings();
        webSettings.setJavaScriptEnabled(true);
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);

        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//getFragmentManager().popBackStack();
                loadFragment(new HomeFragment());

            }
        });
        return view;
    }
    private void loadFragment(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }




}
