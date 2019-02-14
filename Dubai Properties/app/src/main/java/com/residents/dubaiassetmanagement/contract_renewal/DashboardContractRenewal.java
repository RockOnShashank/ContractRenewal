package com.residents.dubaiassetmanagement.contract_renewal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.HomeActivity;
import com.residents.dubaiassetmanagement.MainActivity;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.ServiceRequest.ServicesFragment;
import com.residents.dubaiassetmanagement.contract_renewal.review_proposal.ReviewProposalFragment;

public class DashboardContractRenewal extends Fragment implements View.OnClickListener {
View view;
private TextView fragmentTitle;
    private BottomNavigationView bottomNavigation;
    private Button bt_start;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cr_dashboard, container, false);

        bottomNavigation = (BottomNavigationView) getActivity().findViewById(R.id.navigation_bottom);
        bottomNavigation.setVisibility(View.GONE);
        fragmentTitle = (TextView) getActivity().findViewById(R.id.title_app_bar);
        fragmentTitle.setText("CONTRACT RENEWAL");
        bt_start = (Button) view.findViewById(R.id.bt_start);
        View layout =  getActivity().findViewById(R.id.stepper);
        layout.setVisibility(View.GONE);

        bt_start.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
loadFragmets(new ReviewProposalFragment());

    }
});
       /* ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((HomeActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStack();

            }
        });*/

        return  view;
    }

    private void loadFragmets(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_layout, fragment,"MY_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();

    }




    @Override
    public void onClick(View v) {

    }
}
