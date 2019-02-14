package com.residents.dubaiassetmanagement.ServiceRequest.view_request;

import android.os.Bundle;
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

import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.ServicesFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.history_msr.models.History;
import com.residents.dubaiassetmanagement.ServiceRequest.history_msr.models.ServiceRequest;
import com.residents.dubaiassetmanagement.ServiceRequest.leasing_services.view_request.ViewRequestLSFragment;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class HistoryLSFragment extends Fragment implements ResponseCallback, HistoryAdapter.ItemClickListener{

    private View view;
    private RecyclerView rv_history;
    private TextView fragmentTitle;
    private String serviceResponse = null;
    public HistoryAdapter historyAdapter;
    private History history;
    ArrayList<ServiceRequest> viewRequests;
    Bundle bundle;
    SavePreference mSavePreference;
    private LinearLayout ll_no_request,ll_request;
    private BottomNavigationView bottomNavigation;
    private RelativeLayout iv_bell_icon;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_history_ls, container, false);
        //Set Fragment Title
        bundle = new Bundle();
        bundle =  getArguments();

        rv_history = view.findViewById(R.id.rv_history);
        ll_no_request = view.findViewById(R.id.ll_no_request);
        ll_request =  view.findViewById(R.id.ll_request);

        rv_history.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewRequests = new ArrayList<>();
        mSavePreference = SavePreference.getInstance(getActivity());
        new RequestService(getActivity(), HistoryLSFragment.this).setArguments("Yardi/FetchServiceRequest?tCode="+mSavePreference.getString(IpreferenceKey.TCODE)+"&serviceRequestPageType=2"+"&serviceRequestType="+"3");

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
        //Set Fragment Title
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragments(new ViewRequestLSFragment());
                //getFragmentManager().popBackStack();
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RequestService(getActivity(), HistoryLSFragment.this).setArguments("Yardi/FetchServiceRequest?tCode="+mSavePreference.getString(IpreferenceKey.TCODE)+"&serviceRequestPageType=2"+"&serviceRequestType="+"3");

                mSwipeRefreshLayout.setRefreshing(false);
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
            historyAdapter = new HistoryAdapter(getActivity(), viewRequests);
            historyAdapter.setClickListener(this);
            rv_history.setAdapter(historyAdapter);
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
        loadFragments(new ViewDetailHistoryLC(), bundle);


    }

    private void loadFragments(Fragment fragment, Bundle bundle) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        fragment.setArguments(bundle);
        transaction.commit();

    }

    private void loadFragments(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        fragment.setArguments(bundle);
        transaction.commit();

    }
}

