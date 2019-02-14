package com.residents.dubaiassetmanagement.contract_renewal.confirm_details;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.contract_renewal.ConfirmDetailsStatus;
import com.residents.dubaiassetmanagement.contract_renewal.StepperStatus;
import com.residents.dubaiassetmanagement.contract_renewal.confirm_details.models.InstallmentPlan;
import com.residents.dubaiassetmanagement.contract_renewal.confirm_details.models.InstallmentPlans;
import com.residents.dubaiassetmanagement.contract_renewal.interfaces.ContractRenewalCallback;
import com.residents.dubaiassetmanagement.contract_renewal.payment_schedule.PaymentScheduleFragment;
import com.residents.dubaiassetmanagement.contract_renewal.review_proposal.ReviewProposalFragment;
import com.residents.dubaiassetmanagement.contract_renewal.review_proposal.models.MELeaseChargesV;
import com.residents.dubaiassetmanagement.contract_renewal.review_proposal.models.ReviewProposal;
import com.residents.dubaiassetmanagement.contract_renewal.services.RequestServiceContract;
import com.residents.dubaiassetmanagement.contract_renewal.stepper_model_get.GetStepper;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class ConfirmDetailsFragment  extends Fragment implements ContractRenewalCallback,View.OnClickListener {
private View view;
private SavePreference mSavePreference;
private Button btn_proceed;
InstallmentPlans installmentPlans;
private String noOfInstallments=null;
private TextView tv_text_title;
private GetStepper getStepper;
    private ArrayList<InstallmentPlan> installmentPlanArrayList;
    private Button btn_4,btn_3,btn_2,btn_1;
    private TextView tv_adminFees,tv_ejariFees,tv_premiumInstallments,tv_renewalLeaseAmount,tv_total_amount;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_confirm_details,container,false);
        mSavePreference = SavePreference.getInstance(getActivity());
        new RequestServiceContract(getActivity(), ConfirmDetailsFragment.this).setArgumentsGetStepper("ContractRenewal/Stepper/" + mSavePreference.getString(IpreferenceKey.TCODE));

        btn_1 = (Button) view.findViewById(R.id.btn_1);
        btn_2 = (Button) view.findViewById(R.id.btn_2);
        btn_proceed = (Button) view.findViewById(R.id.btn_proceed);
        btn_3 = (Button) view.findViewById(R.id.btn_3);
        btn_4 = (Button) view.findViewById(R.id.btn_4);
        tv_adminFees = (TextView) view.findViewById(R.id.tv_adminFees);
        tv_ejariFees = (TextView) view.findViewById(R.id.tv_ejariFees);
        tv_premiumInstallments = (TextView) view.findViewById(R.id.tv_premiumInstallments);
        tv_renewalLeaseAmount = (TextView) view.findViewById(R.id.tv_renewalLeaseAmount);
        tv_total_amount = (TextView) view.findViewById(R.id.tv_total_amount);
        tv_text_title = (TextView) view.findViewById(R.id.tv_text_title);
        tv_adminFees.setOnClickListener(this);
        tv_ejariFees.setOnClickListener(this);
        tv_premiumInstallments.setOnClickListener(this);
        tv_renewalLeaseAmount.setOnClickListener(this);
        tv_total_amount.setOnClickListener(this);
        btn_proceed.setOnClickListener(this);

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        new RequestServiceContract(getActivity(), ConfirmDetailsFragment.this).setArguments("ContractRenewal/InstallmentPlan/" + mSavePreference.getString(IpreferenceKey.TCODE));

        EventBus.getDefault().post(new ConfirmDetailsStatus());

    return view;
    }

    @Override
    public void onSuccess(String response) {
        String res = response;
        noOfInstallments="1";
        Gson gson = new GsonBuilder().serializeNulls().create();
        installmentPlans = gson.fromJson(res, InstallmentPlans.class);
        installmentPlanArrayList = new ArrayList<>();
        installmentPlanArrayList.addAll(installmentPlans.getInstallmentPlans());
        installmentPlanArrayList.size();
        Log.e("eeee",""+installmentPlanArrayList.size());
        installmentPlanArrayList.get(0).getNoOfInstallment();
        btn_1.setBackgroundColor(getResources().getColor(R.color.title_text_color));
        btn_1.setTextColor(getResources().getColor(R.color.white)); //SET CUSTOM COLOR

        tv_adminFees.setText(installmentPlanArrayList.get(0).getRenewalProposal().getLeaseCharges().getMELeaseChargesV().get(1).getAmount());
        tv_ejariFees.setText(installmentPlanArrayList.get(0).getRenewalProposal().getLeaseCharges().getMELeaseChargesV().get(0).getAmount());
        tv_premiumInstallments.setText(installmentPlanArrayList.get(0).getRenewalProposal().getRESRenewalSpecials().getMERESRenewalSpecialsV().getCHARGEAMOUNT());
        tv_renewalLeaseAmount.setText(installmentPlanArrayList.get(0).getRenewalProposal().getResRenewalProposal().getMEResRenewalProposalDtlsV().getRENEWALRENT());
        tv_total_amount.setText(installmentPlanArrayList.get(0).getRenewalProposal().getTotalPayableAmount());


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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                noOfInstallments ="1";

                btn_1.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_2.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_3.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_4.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_1.setBackgroundColor(getResources().getColor(R.color.title_text_color));
                btn_1.setTextColor(getResources().getColor(R.color.white)); //SET CUSTOM COLOR

                btn_2.setBackgroundColor(getResources().getColor(R.color.white));
                btn_3.setBackgroundColor(getResources().getColor(R.color.white));
                btn_4.setBackgroundColor(getResources().getColor(R.color.white));
                btn_2.setTextColor(getResources().getColor(R.color.title_text_color));
                btn_3.setTextColor(getResources().getColor(R.color.title_text_color));
                btn_4.setTextColor(getResources().getColor(R.color.title_text_color));
                tv_adminFees.setText(installmentPlanArrayList.get(0).getRenewalProposal().getLeaseCharges().getMELeaseChargesV().get(1).getAmount());
                tv_ejariFees.setText(installmentPlanArrayList.get(0).getRenewalProposal().getLeaseCharges().getMELeaseChargesV().get(0).getAmount());
                tv_premiumInstallments.setText(installmentPlanArrayList.get(0).getRenewalProposal().getRESRenewalSpecials().getMERESRenewalSpecialsV().getCHARGEAMOUNT());
                tv_renewalLeaseAmount.setText(installmentPlanArrayList.get(0).getRenewalProposal().getResRenewalProposal().getMEResRenewalProposalDtlsV().getRENEWALRENT());
                tv_total_amount.setText(installmentPlanArrayList.get(0).getRenewalProposal().getTotalPayableAmount());



                break;
            case R.id.btn_2:
                noOfInstallments = "2";
                btn_1.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_2.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_3.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_4.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_2.setTextColor(getResources().getColor(R.color.white)); //SET CUSTOM COLOR

                btn_2.setBackgroundColor(getResources().getColor(R.color.title_text_color));
                btn_1.setBackgroundColor(getResources().getColor(R.color.white));
                btn_3.setBackgroundColor(getResources().getColor(R.color.white));
                btn_4.setBackgroundColor(getResources().getColor(R.color.white));

                btn_1.setTextColor(getResources().getColor(R.color.title_text_color));
                btn_3.setTextColor(getResources().getColor(R.color.title_text_color));
                btn_4.setTextColor(getResources().getColor(R.color.title_text_color));
                tv_adminFees.setText(installmentPlanArrayList.get(1).getRenewalProposal().getLeaseCharges().getMELeaseChargesV().get(1).getAmount());
                tv_ejariFees.setText(installmentPlanArrayList.get(1).getRenewalProposal().getLeaseCharges().getMELeaseChargesV().get(0).getAmount());
                tv_premiumInstallments.setText(installmentPlanArrayList.get(1).getRenewalProposal().getRESRenewalSpecials().getMERESRenewalSpecialsV().getCHARGEAMOUNT());
                tv_renewalLeaseAmount.setText(installmentPlanArrayList.get(1).getRenewalProposal().getResRenewalProposal().getMEResRenewalProposalDtlsV().getRENEWALRENT());
                tv_total_amount.setText(installmentPlanArrayList.get(1).getRenewalProposal().getTotalPayableAmount());



                break;
            case R.id.btn_3:
                noOfInstallments = "4";
                btn_1.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_2.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_3.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_4.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_3.setTextColor(getResources().getColor(R.color.white)); //SET CUSTOM COLOR

                btn_3.setBackgroundColor(getResources().getColor(R.color.title_text_color));
                btn_1.setBackgroundColor(getResources().getColor(R.color.white));
                btn_2.setBackgroundColor(getResources().getColor(R.color.white));
                btn_4.setBackgroundColor(getResources().getColor(R.color.white));

                btn_2.setTextColor(getResources().getColor(R.color.title_text_color));
                btn_1.setTextColor(getResources().getColor(R.color.title_text_color));
                btn_4.setTextColor(getResources().getColor(R.color.title_text_color));
                tv_adminFees.setText(installmentPlanArrayList.get(2).getRenewalProposal().getLeaseCharges().getMELeaseChargesV().get(1).getAmount());
                tv_ejariFees.setText(installmentPlanArrayList.get(2).getRenewalProposal().getLeaseCharges().getMELeaseChargesV().get(0).getAmount());
                tv_premiumInstallments.setText(installmentPlanArrayList.get(2).getRenewalProposal().getRESRenewalSpecials().getMERESRenewalSpecialsV().getCHARGEAMOUNT());
                tv_renewalLeaseAmount.setText(installmentPlanArrayList.get(2).getRenewalProposal().getResRenewalProposal().getMEResRenewalProposalDtlsV().getRENEWALRENT());
                tv_total_amount.setText(installmentPlanArrayList.get(2).getRenewalProposal().getTotalPayableAmount());



                break;
            case R.id.btn_4:
                noOfInstallments = "12";
                btn_1.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_2.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_3.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_4.setBackground(getResources().getDrawable(R.drawable.border_installments_button));
                btn_4.setTextColor(getResources().getColor(R.color.white)); //SET CUSTOM COLOR

                btn_4.setBackgroundColor(getResources().getColor(R.color.title_text_color));
                btn_1.setBackgroundColor(getResources().getColor(R.color.white));
                btn_3.setBackgroundColor(getResources().getColor(R.color.white));
                btn_2.setBackgroundColor(getResources().getColor(R.color.white));

                btn_2.setTextColor(getResources().getColor(R.color.title_text_color));
                btn_3.setTextColor(getResources().getColor(R.color.title_text_color));
                btn_1.setTextColor(getResources().getColor(R.color.title_text_color));
                tv_adminFees.setText(installmentPlanArrayList.get(4).getRenewalProposal().getLeaseCharges().getMELeaseChargesV().get(1).getAmount());
                tv_ejariFees.setText(installmentPlanArrayList.get(4).getRenewalProposal().getLeaseCharges().getMELeaseChargesV().get(0).getAmount());
                tv_premiumInstallments.setText(installmentPlanArrayList.get(4).getRenewalProposal().getRESRenewalSpecials().getMERESRenewalSpecialsV().getCHARGEAMOUNT());
                tv_renewalLeaseAmount.setText(installmentPlanArrayList.get(4).getRenewalProposal().getResRenewalProposal().getMEResRenewalProposalDtlsV().getRENEWALRENT());
                tv_total_amount.setText(installmentPlanArrayList.get(4).getRenewalProposal().getTotalPayableAmount());



                break;

            case R.id.btn_proceed:
                Bundle bundle = new Bundle();
                bundle.putString("noofInstallment", noOfInstallments);
                bundle.putParcelableArrayList("installmentsList", installmentPlanArrayList);
                loadFragmets(new PaymentScheduleFragment(),bundle);
                break;
        }
    }
    private void loadFragmets(Fragment fragment,Bundle bundle) {

        //load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_layout, fragment,"MY_FRAGMENT");
        transaction.addToBackStack(null);
fragment.setArguments(bundle);
transaction.commit();

    }
}
