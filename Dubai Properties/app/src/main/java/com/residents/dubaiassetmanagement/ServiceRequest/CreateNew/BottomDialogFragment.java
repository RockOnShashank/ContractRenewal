package com.residents.dubaiassetmanagement.ServiceRequest.CreateNew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.R;

import java.util.ArrayList;

public class BottomDialogFragment extends BottomSheetDialogFragment {
ListView lv_adapter;
    private static ArrayList<String> bodyText;
    public static BottomDialogFragment getInstance() {
        return new BottomDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.custom_bottom_sheet, container, false);
lv_adapter = (ListView) view.findViewById(R.id.simpleListView);
       bodyText = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_category, R.id.textViewDialog, bodyText);
        lv_adapter.setAdapter(arrayAdapter);
        ((TextView) view.findViewById(R.id.hello)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// DO SOMETHING
            }
        });
        return view;
    }
    public static BottomDialogFragment addSomeString(ArrayList<String> temp){
        BottomDialogFragment f = new BottomDialogFragment();
        bodyText = temp;
        return f;
    };
}