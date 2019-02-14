package com.residents.dubaiassetmanagement.contract_renewal.review_proposal;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.residents.dubaiassetmanagement.DrawerLocker;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.ViewRequestFragment;
import com.residents.dubaiassetmanagement.ServiceRequest.view_request.models.ViewRequest;
import com.residents.dubaiassetmanagement.Utils.RequestService;
import com.residents.dubaiassetmanagement.contract_renewal.ContractRenewalActivity;
import com.residents.dubaiassetmanagement.contract_renewal.StepperFragment;
import com.residents.dubaiassetmanagement.contract_renewal.StepperStatus;
import com.residents.dubaiassetmanagement.contract_renewal.confirm_details.ConfirmDetailsFragment;
import com.residents.dubaiassetmanagement.contract_renewal.interfaces.ContractRenewalCallback;
import com.residents.dubaiassetmanagement.contract_renewal.rera_index.ReraIndexFragment;
import com.residents.dubaiassetmanagement.contract_renewal.review_proposal.models.LeaseCharges;
import com.residents.dubaiassetmanagement.contract_renewal.review_proposal.models.MELeaseChargesV;
import com.residents.dubaiassetmanagement.contract_renewal.review_proposal.models.ReviewProposal;
import com.residents.dubaiassetmanagement.contract_renewal.services.RequestServiceContract;
import com.residents.dubaiassetmanagement.contract_renewal.stepper_model_get.GetStepper;
import com.residents.dubaiassetmanagement.home_screen.ScheduleFragmentHome;
import com.residents.dubaiassetmanagement.interfaces.ResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class ReviewProposalFragment extends Fragment implements ContractRenewalCallback {
View view;
private SavePreference mSavePreference;
private ArrayList<MELeaseChargesV> reviewProposalArrayList;
ReviewProposal reviewProposal;
private TextView tv_cla,tv_rla,tv_amount_first,tv_amount_second,tv_amount_third, tv_name_first, tv_name_second, tv_name_third,tv_total_amount,tv_rl_amount;
private Button btn_yes;
private GetStepper getStepper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.review_proposal, container, false);
        View layout =  getActivity().findViewById(R.id.stepper);
        layout.setVisibility(View.VISIBLE);

        mSavePreference = SavePreference.getInstance(getActivity());
        new RequestServiceContract(getActivity(), ReviewProposalFragment.this).setArgumentsGetStepper("ContractRenewal/Stepper/" + mSavePreference.getString(IpreferenceKey.TCODE));

        tv_cla = (TextView) view.findViewById(R.id.tv_cla);
        tv_rla = (TextView) view.findViewById(R.id.tv_rla);
        tv_amount_first = (TextView) view.findViewById(R.id.tv_amount_first);
        tv_amount_second = (TextView) view.findViewById(R.id.tv_amount_second);

        tv_amount_third = (TextView) view.findViewById(R.id.tv_amount_third);

        tv_name_first = (TextView) view.findViewById(R.id.tv_name_first);

        tv_name_second = (TextView) view.findViewById(R.id.tv_name_second);
        tv_name_first = (TextView) view.findViewById(R.id.tv_name_first);
        tv_total_amount = (TextView) view.findViewById(R.id.tv_total_amount);
        tv_rl_amount = (TextView) view.findViewById(R.id.tv_rl_amount);
        btn_yes = (Button) view.findViewById(R.id.btn_yes);

        String reraIndex = "<font color='#52a9f6'>RERA INDEX</font>";
String pre = "10% increase on Current Lease Amount based on ";
        tv_rl_amount.setText(Html.fromHtml(pre + reraIndex));
        tv_rl_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadFragmets(new ReraIndexFragment());
            }
        });
        new RequestServiceContract(getActivity(), ReviewProposalFragment.this).setArguments("ContractRenewal/RenewalProposal/" + mSavePreference.getString(IpreferenceKey.TCODE));


    ((DrawerLocker)getActivity()).setDrawerEnabled(true);
        ((ContractRenewalActivity) getActivity()).toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getActivity().getFragmentManager();


                // ((MainActivity) getActivity()).toolbar_title.setText("Afirma Results");
                //((MainActivity) getActivity()).toolbar.setNavigationIcon(null);
                fm.popBackStackImmediate();
            }
        });
        EventBus.getDefault().post(new StepperStatus());
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();

                try {

                    jsonObject.put("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));
                    jsonObject.put("ProposalId", getStepper.getProposalId());
                    jsonObject.put("LeasePeriodInMonths", 0);
                    jsonObject.put("NoOfInstallments", 0);
                    jsonObject.put("TotalPayableAmount", 0);
                    jsonObject.put("ProposalStartDate", reviewProposal.getResRenewalProposal().getMEResRenewalProposalDtlsV().getPROPOSALSTARTDATE());
                    jsonObject.put("ProposalEndDate", reviewProposal.getResRenewalProposal().getMEResRenewalProposalDtlsV().getPROPOSALENDDATE());
                    jsonObject.put("UserState", "1");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
              new RequestServiceContract(getActivity(), ReviewProposalFragment.this).postRequest("ContractRenewal/TrackContractDetails", jsonObject);


            }
        });

        return view;
    }




    @Override
    public void onSuccess(String response) {
        String res = response;

        Gson gson = new GsonBuilder().serializeNulls().create();
        reviewProposal = gson.fromJson(res, ReviewProposal.class);
        reviewProposalArrayList = new ArrayList<>();
        reviewProposalArrayList.addAll(reviewProposal.getLeaseCharges().getMELeaseChargesV());
        tv_cla.setText(reviewProposal.getResRenewalProposal().getMEResRenewalProposalDtlsV().getEXISTINGRENT());
        tv_rla.setText(reviewProposal.getResRenewalProposal().getMEResRenewalProposalDtlsV().getRENEWALRENT());
        for (int i=0;i< reviewProposalArrayList.size();i++){
            if (i==0){
                tv_amount_first.setText(reviewProposalArrayList.get(0).getAmount());
            }
            if (i==1)
            {
                tv_amount_second.setText(reviewProposalArrayList.get(1).getAmount());

            }
            if (i == 2) {

                tv_amount_third.setText(reviewProposalArrayList.get(2).getAmount());

            }
        }
        tv_total_amount.setText(reviewProposal.getTotalPayableAmount());





    }

    @Override
    public void onSuccessGetStepper(String response) {
        String res = response;
        Gson gson = new GsonBuilder().serializeNulls().create();
        getStepper = gson.fromJson(res, GetStepper.class);
        //viewRequests.addAll(getStepper.getSteppers());
    }

    @Override
    public void onPostSuccess(String response) {
        loadFragmets(new ConfirmDetailsFragment());

    }

    private void loadFragmets(Fragment fragment) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_layout, fragment,"MY_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();

    }
}

