package com.residents.dubaiassetmanagement.Profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.residents.dubaiassetmanagement.R;


public class PersonalInfoFragment extends Fragment {

    private static final String TAG ="tab1";
    private Button test1;
    private TextView textview1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);

        //test1=(Button) view.findViewById(R.id.button1);
        //textview1=(TextView)view.findViewById(R.id.textView1) ;
//       test1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                textview1.setText("Hello");
//            }
//        });

        return view;

    }


}
