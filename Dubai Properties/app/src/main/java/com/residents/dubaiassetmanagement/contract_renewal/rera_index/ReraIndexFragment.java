package com.residents.dubaiassetmanagement.contract_renewal.rera_index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.residents.dubaiassetmanagement.R;

public class ReraIndexFragment extends Fragment {

View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.rera_sample,container,false);
        View layout =  getActivity().findViewById(R.id.stepper);
        layout.setVisibility(View.GONE);
    return view;
    }
}
