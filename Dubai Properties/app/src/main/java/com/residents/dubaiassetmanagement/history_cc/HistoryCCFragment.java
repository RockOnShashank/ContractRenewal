package com.residents.dubaiassetmanagement.history_cc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.community_care.view_request.ViewRequestCCFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.history_msr.models.History;
import com.residents.dubaiassetmanagement.ServiceRequest.history_msr.models.ServiceRequest;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.FeedbackFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.HistoryFragment;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.residents.dubaiassetmanagement.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HistoryCCFragment extends Fragment implements ResponseCallback, HistoryCCAdapter.ItemClickListener{

    private View view;
    private RecyclerView rv_history;
    private TextView fragmentTitle;
    private String serviceResponse = null;
    private HistoryCCAdapter HistoryCCAdapter;
    private History history;
    ArrayList<ServiceRequest> viewRequests;
    Bundle bundle;
    SavePreference mSavePreference;
    private LinearLayout ll_no_request,ll_request;
    private BottomNavigationView bottomNavigation;
    private RelativeLayout iv_bell_icon;
    SharedPreferences appSharedPrefs;
    SharedPreferences.Editor prefsEditor;
    SwipeRefreshLayout mSwipeRefreshLayout;

    Gson gson;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_history_cc, container, false);
        //Set Fragment Title
        bundle = new Bundle();
        bundle =  getArguments();

        rv_history = view.findViewById(R.id.rv_history);
        ll_no_request = view.findViewById(R.id.ll_no_request);
        ll_request =  view.findViewById(R.id.ll_request);

        rv_history.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewRequests = new ArrayList<>();
        mSavePreference = SavePreference.getInstance(getActivity());
        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        prefsEditor = appSharedPrefs.edit();

        gson = new Gson();

        if (!appSharedPrefs.getString("CC_History", "").equalsIgnoreCase("")) {
            String json = appSharedPrefs.getString("CC_History", "");
            Type type = new TypeToken<List<ServiceRequest>>(){}.getType();
            viewRequests = gson.fromJson(json, type);

            HistoryCCAdapter = new HistoryCCAdapter(getActivity(), viewRequests);
            HistoryCCAdapter.setClickListener(this);
            rv_history.setAdapter(HistoryCCAdapter);


        }else {

            new RequestService(getActivity(), HistoryCCFragment.this).setArguments("Yardi/FetchServiceRequest?tCode=" + mSavePreference.getString(IpreferenceKey.TCODE) + "&serviceRequestPageType=2" + "&serviceRequestType=" + "2");
        }




        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RequestService(getActivity(), HistoryCCFragment.this).setArguments("Yardi/FetchServiceRequest?tCode=" + mSavePreference.getString(IpreferenceKey.TCODE) + "&serviceRequestPageType=2" + "&serviceRequestType=" + "2");

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("HISTORY");
        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);
        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);

        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmentafterCancel(new ViewRequestCCFragment());
                //getFragmentManager().popBackStack();

            }
        });


    }

    @Override
    public void onSuccess(String response) {
        serviceResponse = response;
        if (serviceResponse.equalsIgnoreCase("")){
            ll_no_request.setVisibility(View.VISIBLE);
            ll_request.setVisibility(View.GONE);
        }else {
//Toast.makeText(getActivity(),"Success"+response,Toast.LENGTH_LONG).show();
            viewRequests.clear();
            ll_no_request.setVisibility(View.GONE);
            ll_request.setVisibility(View.VISIBLE);
            Gson gson = new GsonBuilder().serializeNulls().create();
            history = gson.fromJson(serviceResponse, History.class);
            viewRequests.addAll(history.getServiceRequests());
            HistoryCCAdapter = new HistoryCCAdapter(getActivity(), viewRequests);
            HistoryCCAdapter.setClickListener(this);
            rv_history.setAdapter(HistoryCCAdapter);


            //save in shared preference
            String json = gson.toJson(viewRequests);
            prefsEditor.putString("CC_History", json);
            prefsEditor.commit();




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

    }

    @Override
    public void onPostSuccess(String response, String sessionId) {

    }

    @Override
    public void onItemClick(View view, int position) {


        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("viewRequestList",viewRequests );
        bundle.putInt("position",position);
        loadFragments(new ViewDetailHistoryCC(), bundle);






    }

    private void loadFragments(Fragment fragment, Bundle bundle) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        fragment.setArguments(bundle);
        transaction.commit();

    }
    private void loadFragmentafterCancel(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
