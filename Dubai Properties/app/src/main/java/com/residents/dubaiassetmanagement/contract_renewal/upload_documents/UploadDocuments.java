package com.residents.dubaiassetmanagement.contract_renewal.upload_documents;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.residents.dubaiassetmanagement.R;
import com.residents.dubaiassetmanagement.SavePreferences.IpreferenceKey;
import com.residents.dubaiassetmanagement.SavePreferences.SavePreference;
import com.residents.dubaiassetmanagement.contract_renewal.confirm_details.ConfirmDetailsFragment;
import com.residents.dubaiassetmanagement.contract_renewal.interfaces.ContractRenewalCallback;
import com.residents.dubaiassetmanagement.contract_renewal.review_proposal.ReviewProposalFragment;
import com.residents.dubaiassetmanagement.contract_renewal.services.RequestServiceContract;
import com.residents.dubaiassetmanagement.contract_renewal.stepper_model_get.GetStepper;

import org.json.JSONException;
import org.json.JSONObject;

public class UploadDocuments extends Fragment implements ContractRenewalCallback {
View view;
Button saveButton;
private GetStepper getStepper;
private SavePreference mSavePreference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.fragment_upload,container,false);
        saveButton = (Button) view.findViewById(R.id.saveButton);

mSavePreference = SavePreference.getInstance(getActivity());
        new RequestServiceContract(getActivity(), UploadDocuments.this).setArgumentsGetStepper("ContractRenewal/Stepper/" + mSavePreference.getString(IpreferenceKey.TCODE));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();

                try {

                    jsonObject.put("ProfileId",mSavePreference.getString(IpreferenceKey.TCODE));
                    jsonObject.put("NoOfInstallments", 0);
                    jsonObject.put("TotalPayableAmount", 0);
                    jsonObject.put("UserState", "2");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new RequestServiceContract(getActivity(), UploadDocuments.this).postRequest("ContractRenewal/TrackContractDetails", jsonObject);

            }
        });
        return view;
    }

    @Override
    public void onSuccess(String response) {

    }

    @Override
    public void onSuccessGetStepper(String response) {
        String res = response;
        Gson gson = new GsonBuilder().serializeNulls().create();
        getStepper = gson.fromJson(res, GetStepper.class);
    }

    @Override
    public void onPostSuccess(String response) {
//Start step 3
    }
}
