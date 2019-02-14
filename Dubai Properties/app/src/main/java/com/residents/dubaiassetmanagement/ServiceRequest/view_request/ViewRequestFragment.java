package com.residents.dubaiassetmanagement.ServiceRequest.view_request;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.reflect.TypeToken;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.Profile.HouseHelpAdapter;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.CreateNew.CreateNewFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.ServicesFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.ServiceRequest;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.ViewRequest;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.residents.dubaiassetmanagement.R;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewRequestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewRequestFragment extends Fragment implements View.OnClickListener, ResponseCallback, ViewRequestAdapter.ItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private SavePreference mSavePreference;
    private View view;
    private ViewRequest viewRequest;
    private RecyclerView rv_maintenance_request;
    private OnFragmentInteractionListener mListener;
    private ViewRequestAdapter viewRequestAdapter;
    ArrayList<ServiceRequest> viewRequests;
    private ImageView iv_openCreateNew;
    ArrayList<ServiceRequest> newViewRequest;

    private String serviceResponse = null;

    private TextView fragmentTitle, tv_view_history;
    private ImageView iv_back_button;
    RelativeLayout iv_bell_icon;
    SharedPreferences appSharedPrefs;
    SharedPreferences.Editor prefsEditor;
    Gson gson;
    private ActionBarDrawerToggle toggle;
    private BottomNavigationView bottomNavigation;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayout ll_no_request;

    public ViewRequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewRequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewRequestFragment newInstance(String param1, String param2) {

        ViewRequestFragment fragment = new ViewRequestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.maintenance_request_screen, container, false);
        mSavePreference = SavePreference.getInstance(getActivity());
        viewRequests = new ArrayList<>();
        newViewRequest = new ArrayList<>();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RequestService(getActivity(), ViewRequestFragment.this).setArguments("Yardi/FetchServiceRequest?tCode=" + mSavePreference.getString(IpreferenceKey.TCODE) + "&serviceRequestPageType=1&serviceRequestType=1");

                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("MAINTENANCE REQUESTS");
        //Set Fragment Title
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmets(new ServicesFragment());
                //getFragmentManager().popBackStack();
            }
        });
        appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        prefsEditor = appSharedPrefs.edit();

        gson = new Gson();



        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();

        prefsEditor.putString("MSR_History", "");

        prefsEditor.commit();



        iv_openCreateNew = view.findViewById(R.id.iv_openCreateNew);
        tv_view_history = view.findViewById(R.id.tv_view_history);
        rv_maintenance_request = view.findViewById(R.id.rv_maintenance_request);
        ll_no_request = view.findViewById(R.id.ll_no_request);
        rv_maintenance_request.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager linearLayoutManager_house = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecorationhouse = new DividerItemDecoration(rv_maintenance_request.getContext(),
                linearLayoutManager_house.getOrientation());
        //  rv_maintenance_request.addItemDecoration(dividerItemDecorationhouse);
        iv_openCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragments(new CreateNewFragment());
            }
        });
        tv_view_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("value", "1");
                loadFragment(new HistoryFragment(), bundle);
            }
        });

        if (!appSharedPrefs.getString("MyObject1", "").equalsIgnoreCase("")) {

            String json = appSharedPrefs.getString("MyObject1", "");
            Type type = new TypeToken<List<ServiceRequest>>(){}.getType();
            newViewRequest = gson.fromJson(json, type);
            viewRequestAdapter = new ViewRequestAdapter(getActivity(), newViewRequest);
            viewRequestAdapter.setClickListener(this);
            rv_maintenance_request.setAdapter(viewRequestAdapter);
            Log.e("dafd",""+newViewRequest);

        }else {
            new RequestService(getActivity(), ViewRequestFragment.this).setArguments("Yardi/FetchServiceRequest?tCode=" + mSavePreference.getString(IpreferenceKey.TCODE) + "&serviceRequestPageType=1&serviceRequestType=1");
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("MAINTENANCE REQUESTS");


        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);

        iv_bell_icon = (RelativeLayout) getActivity().findViewById(R.id.iv_bell_icon);
        iv_bell_icon.setVisibility(View.INVISIBLE);

        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmets(new ServicesFragment());
                //getFragmentManager().popBackStack();

            }
        });

        /*((DrawerLocker) getActivity()).setDrawerEnabled(false);

        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawerLocker) getActivity()).setDrawerOpen(true);
            }
        });*/
        //  ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        iv_back_button = (ImageView) getActivity().findViewById(R.id.iv_back_button);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(String response) {
        serviceResponse = response;
        if (serviceResponse.equalsIgnoreCase("")) {
            ll_no_request.setVisibility(View.VISIBLE);
          //  Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_LONG).show();
        } else {
            ll_no_request.setVisibility(View.GONE);
            viewRequests.clear();
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            prefsEditor.putString("MyObject1", "");

            prefsEditor.putString("CC_List", "");
            prefsEditor.putString("LS_List", "");
            Gson gson = new GsonBuilder().serializeNulls().create();
            viewRequest = gson.fromJson(serviceResponse, ViewRequest.class);
            viewRequests.addAll(viewRequest.getServiceRequests());
            viewRequestAdapter = new ViewRequestAdapter(getActivity(), viewRequests);
            viewRequestAdapter.setClickListener(this);
            rv_maintenance_request.setAdapter(viewRequestAdapter);


            //save in shared preference
            String json = gson.toJson(viewRequests);
            prefsEditor.putString("MyObject1", json);
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
    public void onItemClick(View view, int position) {
        //    Toast.makeText(getActivity(),"Success"+position,Toast.LENGTH_LONG).show();

        Bundle bundle = new Bundle();
        newViewRequest.addAll(viewRequests);

        if (!appSharedPrefs.getString("MyObject1", "").equalsIgnoreCase("")) {
            bundle.putParcelableArrayList("viewRequestList", newViewRequest);
            bundle.putInt("position", position);
            bundle.putInt("isViewRequest", position);
            loadFragment(new ViewRequestDetailFragment(), bundle);
        }else {
            bundle.putParcelableArrayList("viewRequestList", viewRequests);
            bundle.putInt("position", position);
            bundle.putInt("isViewRequest", position);
            loadFragment(new ViewRequestDetailFragment(), bundle);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void loadFragment(Fragment fragment, Bundle bundle) {

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
        transaction.add(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    private void loadFragmets(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void onPostSuccess(String response, String sessionId) {

    }

}
