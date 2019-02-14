package com.residents.dubaiassetmanagement.contract_renewal.payment_schedule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.contract_renewal.confirm_details.models.InstallmentPlan;
import com.residents.dubaiassetmanagement.contract_renewal.upload_documents.UploadDocuments;

import java.util.ArrayList;

public class PaymentScheduleFragment extends Fragment implements PaymentScheduleAdapter.ItemClickListener{
    View view;
    Button btn_next;
    private RecyclerView rv_payment_schedule;
    private PaymentScheduleAdapter paymentScheduleAdapter;
    private Bundle bundle;
    private ArrayList<InstallmentPlan> installmentlist;
    private String noOfInstallments=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.payment_schedule,container,false);
        btn_next = (Button) view.findViewById(R.id.btn_next);
        rv_payment_schedule = (RecyclerView) view.findViewById(R.id.rv_payment_schedule);
        bundle = new Bundle();
        bundle=getArguments();
        installmentlist = new ArrayList<InstallmentPlan>();
        noOfInstallments = bundle.getString("noofInstallment");
        installmentlist = bundle.getParcelableArrayList("installmentsList");

        // set up the RecyclerView
        rv_payment_schedule.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (noOfInstallments!=null){
            paymentScheduleAdapter = new PaymentScheduleAdapter(getActivity(),installmentlist,noOfInstallments);
            paymentScheduleAdapter.setClickListener(this);
            rv_payment_schedule.setAdapter(paymentScheduleAdapter);
        }


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragmets(new UploadDocuments());
            }
        });
        return view;
    }
    @Override
    public void onItemClick(View view, int position) {
    }
    private void loadFragmets(Fragment fragment) {
        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_layout, fragment,"MY_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();

    }
}
